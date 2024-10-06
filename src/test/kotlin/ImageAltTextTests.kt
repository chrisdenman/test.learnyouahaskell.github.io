import io.github.learnyouahaskell.test.HostedPages
import io.github.learnyouahaskell.test.config.loadFrom
import org.junit.jupiter.api.Test
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.lang.System.getProperty
import java.net.URI
import org.openqa.selenium.By.xpath as byXPath
import java.net.URI.create as createUri

/**
 * Ensures that all images have the expected 'alt' attribute.
 */
class ImageAltTextTests {

    private val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))
    private val hostedPages: HostedPages = HostedPages(configuration)
    private val hostedImages: HostedImages = HostedImages()
    private val externalImages: ExternalImages = ExternalImages()
    private val imageUriToAltText: Map<URI, String> =
        hostedImages.all.associate { it.relativeUri to it.altText } +
                externalImages.all.associate { it.relativeUri to it.altText }

    @Test
    fun `That all images have the expected alt attribute`() {
        var imageAltAttributesCorrect = true

        val parentUri: URI = hostedPages.landing.uri

        hostedPages.all().forEach { page ->
            RemoteWebDriver(
                configuration.tests.remoteWebDriver.uri.toURL(),
                FirefoxOptions()
            ).run {
                AutoCloseable { quit() }.use {
                    get(page.uri.toString())

                    findElements(byXPath("//img")).forEach { imageElement ->
                        val srcAttribute = imageElement.getAttribute("src")

                        if (srcAttribute == null || srcAttribute.isBlank()) {
                            println(
                                "Image ${imageElement.getAttribute("outerHTML")} on $page has no or a blank " +
                                        "'src' attribute value."
                            )
                            imageAltAttributesCorrect = false
                        } else {
                            val altAttribute = imageElement.getAttribute("alt")
                            if (altAttribute == null || altAttribute.isBlank()) {
                                println(
                                    "Image ${imageElement.getAttribute("outerHTML")} on $page has no or a blank " +
                                            "'alt' attribute value."
                                )
                                imageAltAttributesCorrect = false
                            } else {

                                val srcUri = createUri(srcAttribute)
                                val relativeSrcAttributeElseAbsolute = createUri(parentUri.toString())
                                    .relativize(srcUri)

                                if (imageUriToAltText.containsKey(relativeSrcAttributeElseAbsolute)) {
                                    val expectedAltText = imageUriToAltText[relativeSrcAttributeElseAbsolute]!!
                                    if (expectedAltText != altAttribute) {
                                        println(
                                            "Image ${imageElement.getAttribute("outerHTML")} on $page has an " +
                                                    "erroneous 'alt' attribute:\n'${expectedAltText}' (expected)\n" +
                                                    "'${altAttribute}' (actual)."
                                        )
                                        imageAltAttributesCorrect = false
                                    }
                                } else {
                                    println("Unknown image ${imageElement.getAttribute("outerHTML")} on $page.")
                                    imageAltAttributesCorrect = false
                                }
                            }
                        }
                    }
                }
            }
        }

        assert(imageAltAttributesCorrect)
    }
}