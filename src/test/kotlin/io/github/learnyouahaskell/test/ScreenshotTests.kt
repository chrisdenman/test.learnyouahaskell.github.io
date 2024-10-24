package io.github.learnyouahaskell.test

import com.assertthat.selenium_shutterbug.core.Capture.FULL_SCROLL
import com.assertthat.selenium_shutterbug.core.PageSnapshot
import com.assertthat.selenium_shutterbug.core.Shutterbug.shootPage
import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.configuration.FilenameTemplateProperties
import io.github.learnyouahaskell.test.configuration.interpolate
import io.github.learnyouahaskell.test.util.BufferedImageUtils.Companion.getVerticalTiles
import io.github.learnyouahaskell.test.util.FileUtils.Companion.dirExistsOrCreated
import org.junit.jupiter.api.Test
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.nio.file.FileSystems.getDefault
import java.time.LocalDateTime
import javax.imageio.ImageIO

class ScreenshotTests {

    val cs = ConfigurationSupport
    val ss = SeleniumSupport()
    val config = cs.config.tests.screenshots

    private fun buildTileFile(properties: FilenameTemplateProperties): File =
        File(interpolate(config.outputFileTemplate, properties).replace("/", getDefault().separator))

    private fun takeSnapshot(webDriver: RemoteWebDriver): PageSnapshot =
        shootPage(webDriver, FULL_SCROLL, config.scrollTimeoutMilliseconds.toInt(), true)

    @Test
    fun `That we are able to take screenshots of all pages`() {
        val startTime = LocalDateTime.now()!!

        ss.drive(cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            config.relativePageUrls.forEach { relativePageUrl ->
                get(cs.config.httpsUri.resolve(relativePageUrl).toString())
                val snapshot = takeSnapshot(this)
                val browserWindow = manage().window()
                val platformName = capabilities.platformName
                getVerticalTiles(snapshot.image, config.maximumHeightPixels.toInt())
                    .forEachIndexed { tileIndex, tile ->
                        val outputFile =
                            buildTileFile(
                                FilenameTemplateProperties(cs.config).apply {
                                    capabilities.let { capabilities ->
                                        addStartTimeUtc(startTime)
                                        addRelativePageUrl(relativePageUrl)
                                        addBrowserProperties(
                                            browser.browserName(),
                                            browserWindow.size.width,
                                            browserWindow.size.height,
                                            browserWindow.position.x,
                                            browserWindow.position.y,
                                            capabilities.browserVersion
                                        )
                                        addPlatformProperties(
                                            platformName.name,
                                            platformName.majorVersion,
                                            platformName.minorVersion
                                        )
                                        addTileIndex(tileIndex)
                                    }
                                }
                            )

                        require(dirExistsOrCreated(outputFile.parentFile))

                        ImageIO.write(
                            tile,
                            config.imageFormat.toString(),
                            outputFile
                        )

                        require(outputFile.exists())
                    }
            }
        }
    }
}
