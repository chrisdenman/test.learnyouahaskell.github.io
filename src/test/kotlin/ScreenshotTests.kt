package io.github.learnyouahaskell.test

import ConfigurationInterpolationProperties
import com.assertthat.selenium_shutterbug.core.Capture
import com.assertthat.selenium_shutterbug.core.PageSnapshot
import com.assertthat.selenium_shutterbug.core.Shutterbug.shootPage
import io.github.learnyouahaskell.test.config.Configuration
import io.github.learnyouahaskell.test.config.SeleniumBrowserStereotype
import io.github.learnyouahaskell.test.config.loadFrom
import io.github.learnyouahaskell.test.util.BufferedImageUtils.Companion.getVerticalTiles
import io.github.learnyouahaskell.test.util.FileUtils
import org.junit.jupiter.api.Test
import org.openqa.selenium.Capabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.ie.InternetExplorerOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.lang.System.getProperty
import java.nio.file.FileSystems.getDefault
import java.time.LocalDateTime
import javax.imageio.ImageIO
import kotlin.reflect.KClass
import kotlin.test.Ignore

class ScreenshotTests {

    val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))

    private fun buildTileFile(properties: ConfigurationInterpolationProperties): File =
        File(properties.get().entries.fold(configuration.tests.screenshots.outputFileTemplate) { acc, (k, v) ->
            acc.replace("{{${k}}}", v.toString())
        }.replace("/", getDefault().separator))

    private fun takeSnapshot(webDriver: RemoteWebDriver, configuration: Configuration): PageSnapshot =
        shootPage(
            webDriver,
            Capture.FULL_SCROLL,
            configuration.tests.screenshots.scrollTimeoutMilliseconds.toInt(),
            true
        )

    @Test
    fun `That we are able to take screenshots of all pages`() {
        val startTime = LocalDateTime.now()!!
        val testsConfig = configuration.tests
        val browsersConfig = testsConfig.browsers
        testsConfig.relativePageUrls.forEach { relativePageUrl ->
            browsersConfig.targets.forEach { browserTarget ->
                val webDriver: RemoteWebDriver = RemoteWebDriver(
                    testsConfig.remoteWebDriver.uri.toURL(),
                    browserStereotypeNameToCapabilities[browserTarget.name]!!
                        .constructors
                        .first()
                        .call(),
                    IS_WEBDRIVER_TRACING_ENABLED
                )
                AutoCloseable { webDriver.quit() }.use {
                    browsersConfig
                        .dimensions
                        .forEach { dimension ->
                            val requestedDimension = Dimension(dimension.width.toInt(), dimension.height.toInt())
                            webDriver.get(configuration.serving.uri.resolve(relativePageUrl).toString())
                            webDriver.manage().window().size = requestedDimension

                            val snapshot = takeSnapshot(webDriver, configuration)

                            getVerticalTiles(
                                snapshot.image,
                                testsConfig.screenshots.maximumHeightPixels.toInt()
                            ).run {

                                forEachIndexed { tileIndex, tile ->
                                    val outputFile =
                                        buildTileFile(
                                            ConfigurationInterpolationProperties(configuration).apply {
                                                addStartTimeUtc(startTime)
                                                addRelativePageUrl(relativePageUrl)
                                                addBrowserProperties(browserTarget, dimension)
                                                addWebDriverProperties(webDriver)
                                                addTileIndex(tileIndex)
                                            }
                                        )

                                    assert(FileUtils.dirExistsOrCreated(outputFile.parentFile))

                                    ImageIO.write(
                                        tile,
                                        configuration.tests.screenshots.imageFormat.toString(),
                                        outputFile
                                    )

                                    assert(outputFile.exists())
                                }
                            }
                        }
                }
            }
        }
    }

    companion object {
        val browserStereotypeNameToCapabilities: Map<SeleniumBrowserStereotype, KClass<out Capabilities>> = mapOf(
            SeleniumBrowserStereotype("firefox") to FirefoxOptions::class,
            SeleniumBrowserStereotype("chrome") to ChromeOptions::class,
            SeleniumBrowserStereotype("edge") to EdgeOptions::class,
            SeleniumBrowserStereotype("ie") to InternetExplorerOptions::class
        )

        const val IS_WEBDRIVER_TRACING_ENABLED = false
    }
}
