import io.github.learnyouahaskell.test.HostedPages
import io.github.learnyouahaskell.test.config.loadFrom
import org.junit.jupiter.api.Test
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.lang.System.getProperty
import java.net.URI
import javax.imageio.ImageIO
import org.openqa.selenium.By.xpath as byXPath

/**
 * Ensures that all images are declared with their actual widths and heights.
 */
class ImageSizeTests {

    companion object {
        private fun isInt(text: String): Boolean = text.toIntOrNull() != null
    }

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

                    findElements(byXPath("//img")).forEach { imageElement ->
                        var imageAttributesCorrect = true
                        try {
                            val widthAttribute = imageElement.getAttribute("width")
                            if (widthAttribute == null || widthAttribute.isEmpty() || !isInt(widthAttribute)) {
                                println("Image ${imageElement.getAttribute("outerHTML")} on $page has an erroneous 'width' attribute.")
                                imageAttributesCorrect = false
                            }

                            val heightAttribute = imageElement.getAttribute("height")
                            if (heightAttribute == null || heightAttribute.isEmpty() || !isInt(heightAttribute)) {
                                println("Image $imageElement on $page has an erroneous 'height' attribute.")
                                imageAttributesCorrect = false
                            }

                            val srcAttribute = imageElement.getAttribute("src")
                            if (srcAttribute == null || srcAttribute.isEmpty()) {
                                println("Image ${imageElement.getAttribute("outerHTML")} on $page has an erroneous 'src' attribute.")
                                imageAttributesCorrect = false
                            }

                            if (imageAttributesCorrect) {
                                val image = ImageIO.read(URI.create(srcAttribute!!).toURL())
                                val actualWidth = image.width
                                val actualHeight = image.height

                                if (actualWidth != widthAttribute!!.toInt()) {
                                    println("Image ${imageElement.getAttribute("outerHTML")} on $page has declared a width of $widthAttribute but its actual width is $actualWidth.")
                                    imageSizeDeclarationsAreCorrect = false
                                }

                                if (actualHeight != heightAttribute!!.toInt()) {
                                    println("Image ${imageElement.getAttribute("outerHTML")} on $page has declared a height of $heightAttribute but its actual height is $actualHeight.")
                                    imageSizeDeclarationsAreCorrect = false
                                }
                            } else {
                                imageSizeDeclarationsAreCorrect = false
                            }
                        } catch (throwable: Throwable) {
                            println(throwable)
                            throw  throwable
                        }
                    }
                }
            }
        }

        assert(imageSizeDeclarationsAreCorrect) { "There are images with incorrectly declared dimensions." }
    }
}