import io.github.learnyouahaskell.test.config.BrowserTarget
import io.github.learnyouahaskell.test.config.Configuration
import io.github.learnyouahaskell.test.config.Dimension
import org.openqa.selenium.remote.RemoteWebDriver
import java.time.LocalDateTime
import java.time.ZoneOffset
import kotlin.collections.mutableMapOf
import kotlin.collections.set

class ConfigurationInterpolationProperties(val configuration: Configuration) {

    private fun addJavaSystemProperties(): ConfigurationInterpolationProperties =
        this.apply {
            System.getProperties().forEach { (key, value) ->
                properties.put(key.toString(), value)
            }
        }

    private fun addEnvironmentVariables() =
        this.apply {
            properties.putAll(System.getenv())
        }

    private val properties: MutableMap<String, Any> = mutableMapOf<String, Any>()

    init {
        addJavaSystemProperties()
            .addJavaSystemProperties()
            .addEnvironmentVariables()
            .addConfigurationProperties(configuration)
    }

    fun addStartTimeUtc(now: LocalDateTime) {
        properties["start_time_utc"] = now
            .atOffset(ZoneOffset.UTC)
            .format(configuration.tests.screenshots.dateTimeFormatter)
    }

    fun addRelativePageUrl(url: String) {
        properties["relative_page_url"] = if (url.isEmpty()) "ROOT" else url
    }

    fun addBrowserProperties(browserTarget: BrowserTarget, dimension: Dimension) {
        properties["browser.name"] = browserTarget.name.toString()
        dimension.run {
            properties["browser.width"] = width.toInt()
            properties["browser.height"] = height.toInt()
        }
    }

    fun addTileIndex(index: Int) {
        properties["tile.index"] = index
    }

    fun addWebDriverProperties(webDriver: RemoteWebDriver) {
        webDriver.run {
            manage().window().position.run {
                properties["browser.x"] = x
                properties["browser.y"] = y
            }

            capabilities.run {
                properties["browser.version"] = browserVersion
                platformName.run {
                    properties["platform.name"] = name
                    properties["platform.major_version"] = majorVersion
                    properties["platform.minor_version"] = minorVersion
                }
            }
        }
    }

    fun addConfigurationProperties(configuration: Configuration) {
        configuration.run {
            properties.putAll(
                listOf<Pair<String, Any>>(
                    "id" to id,
                    "github.owner" to github.owner,
                    "github.repository" to github.repository,
                    "github.html_content_root" to github.htmlContentRoot,
                    "github.branch" to github.branch,
                    "github.sha" to github.sha,
                    "screenshots.maximum_height_pixels" to tests.screenshots.maximumHeightPixels.toInt(),
                    "screenshots.image_format" to tests.screenshots.imageFormat.toString()
                )
            )
        }
    }

    fun get(): Map<String, Any> = properties.toMap()
}