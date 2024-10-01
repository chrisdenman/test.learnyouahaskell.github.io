import io.github.learnyouahaskell.test.HostedPages
import io.github.learnyouahaskell.test.config.loadFrom
import org.junit.jupiter.api.Test
import org.openqa.selenium.By.xpath as byXPath
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.lang.System.getProperty
import java.net.URI
import javax.imageio.ImageIO
import kotlin.toString

class ImageSizeTests {

    private val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))
    private val hostedPages: HostedPages = HostedPages(configuration)

    @Test
    fun `That all images on our hosted pages are declared with their actual dimensions`() {
        var imageSizeDeclarationsAreCorrect = true

        hostedPages.all().forEach { page ->
            RemoteWebDriver(
                configuration.tests.remoteWebDriver.uri.toURL(),
                FirefoxOptions()
            ).run {
                AutoCloseable { quit() }.use {
                    get(page.uri.toString())

                    findElements(byXPath("//img")).forEach {
                        val widthAttribute = it.getAttribute("width")
                        if (widthAttribute == null || widthAttribute.isEmpty()) {
                            println("Image $it on $page has an erroneous width attribute.")
                            imageSizeDeclarationsAreCorrect = false
                        }

                        val heightAttribute = it.getAttribute("height")
                        if (heightAttribute == null || heightAttribute.isEmpty()) {
                            println("Image $it on $page has an erroneous height attribute.")
                            imageSizeDeclarationsAreCorrect = false
                        }

                        val srcAttribute = it.getAttribute("src")
                        if (srcAttribute == null || srcAttribute.isEmpty()) {
                            println("Image $it on $page has an erroneous src attribute.")
                            imageSizeDeclarationsAreCorrect = false
                        }

                        val image = ImageIO.read(URI.create(it.getAttribute("src")!!).toURL())
                        val actualWidth = image.width
                        val actualHeight = image.height

                        if (actualWidth != widthAttribute!!.toInt()) {
                            println("Image $it on $page is declared with a width of $widthAttribute but its actual width is $actualWidth.")
                            imageSizeDeclarationsAreCorrect = false
                        }

                        if (actualHeight != heightAttribute!!.toInt()) {
                            println("Image $it on $page is declared with a height of $heightAttribute but its actual height is $actualHeight.")
                            imageSizeDeclarationsAreCorrect = false
                        }
                    }
                }
            }
        }

        assert(imageSizeDeclarationsAreCorrect)
    }
}