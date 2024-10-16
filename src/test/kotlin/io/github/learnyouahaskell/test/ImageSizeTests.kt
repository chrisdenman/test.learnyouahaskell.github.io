package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.isAttributeDeclared
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.isPositiveIntAttribute
import io.github.learnyouahaskell.test.app.SslSupport
import org.junit.jupiter.api.Test
import java.io.IOException
import java.net.MalformedURLException
import java.net.URI
import javax.imageio.ImageIO
import javax.net.ssl.HttpsURLConnection
import org.openqa.selenium.By.xpath as byXPath

/**
 * Ensures that all images are declared with their actual widths and heights.
 */
class ImageSizeTests {

    val cs = ConfigurationSupport
    val ss = SeleniumSupport()

    @Test
    fun `That all images on our hosted pages are declared with their actual dimensions`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            var imageSizeDeclarationsAreCorrect = true
            cs.hostedPages.all().forEach { page ->
                get(page.uriAsText)
                findElements(byXPath("//img")).forEach { imageElement ->
                    println("${imageElement.getAttribute("outerHTML")}")

                    // @todo use a fold to deal with imageAttributesCorrect

                    var imageAttributesCorrect = true
                    if (!isPositiveIntAttribute(imageElement, "width")) {
                        println("${imageElement.getAttribute("outerHTML")} on $page has an erroneous 'width' attribute.")
                        imageAttributesCorrect = false
                    }

                    if (!isPositiveIntAttribute(imageElement, "height")) {
                        println("$imageElement on $page has an erroneous 'height' attribute.")
                        imageAttributesCorrect = false
                    }

                    if (!isAttributeDeclared(imageElement, "src")) {
                        println("${imageElement.getAttribute("outerHTML")} on $page has an erroneous 'src' attribute.")
                        imageAttributesCorrect = false
                    }

                    if (imageAttributesCorrect) {
                        try {
                            val x = URI
                                .create(imageElement.getAttribute("src")!!)
                                .toURL()
                            HttpsURLConnection.setDefaultSSLSocketFactory(SslSupport.sslContext?.socketFactory)

                            x
                                .openStream()
                                .use {
                                    val image = ImageIO.read(it)

                                    val actualWidth = image.width
                                    val actualHeight = image.height

                                    imageElement.getAttribute("width")!!.toInt().let { declaredWidth ->
                                        if (actualWidth != declaredWidth) {
                                            println("${imageElement.getAttribute("outerHTML")} on $page has declared a width of $declaredWidth but its actual width is $actualWidth.")
                                            imageSizeDeclarationsAreCorrect = false
                                        }
                                    }

                                    imageElement.getAttribute("height")!!.toInt().let { declaredHeight ->
                                        if (actualHeight != declaredHeight) {
                                            println("${imageElement.getAttribute("outerHTML")} on $page has declared a height of $declaredHeight but its actual height is $actualHeight.")
                                            imageSizeDeclarationsAreCorrect = false
                                        }
                                    }
                                }
                        } catch (e: IOException) {
                            println("${imageElement.getAttribute("outerHTML")} on $page generated an exception $e")
                            imageSizeDeclarationsAreCorrect = false
                        } catch (e: MalformedURLException) {
                            println("${imageElement.getAttribute("outerHTML")} on $page generated an exception $e")
                            imageSizeDeclarationsAreCorrect = false
                        }
                    } else {
                        imageSizeDeclarationsAreCorrect = false
                    }
                }
            }
            assert(imageSizeDeclarationsAreCorrect) { "There are images with incorrectly declared dimensions." }
        }
}