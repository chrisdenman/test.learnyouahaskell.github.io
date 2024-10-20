package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.isAttributeDeclared
import io.github.learnyouahaskell.test.site.ExternalImages
import io.github.learnyouahaskell.test.site.HostedImages
import org.junit.jupiter.api.Test
import java.net.URI
import org.openqa.selenium.By.xpath as byXPath
import java.net.URI.create as createUri

/**
 * Ensures that all images have the expected 'alt' attribute.
 */
class ImageAltTextTests {

    val cs = ConfigurationSupport
    val ss = SeleniumSupport()

    private val hostedImages: HostedImages = HostedImages()
    private val externalImages: ExternalImages = ExternalImages()
    private val imageUriToAltText: Map<URI, String> =
        hostedImages.all.associate { it.relativeUri to it.altText } +
                externalImages.all.associate { it.relativeUri to it.altText }

    @Test
    fun `That all images have the expected alt attribute`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            var imageAltAttributesCorrect = true
            cs.hostedPages.all().forEach { page ->
                get(page.uriAsText)
                val parentUri: URI = cs.hostedPages.landing.uri

                findElements(byXPath("//img")).forEach { imageElement ->
                    if (!isAttributeDeclared(imageElement, "src")) {
                        println("${imageElement.getAttribute("outerHTML")} on $page has an erroneous 'src' attribute value.")
                        imageAltAttributesCorrect = false
                    } else {
                        if (!isAttributeDeclared(imageElement, "alt")) {
                            println("${imageElement.getAttribute("outerHTML")} on $page has an erroneous 'alt' attribute value.")
                            imageAltAttributesCorrect = false
                        } else {
                            val srcUri = createUri(imageElement.getAttribute("src")!!)
                            val relativeSrcAttributeElseAbsolute =
                                createUri(parentUri.toString()).relativize(srcUri)

                            if (imageUriToAltText.containsKey(relativeSrcAttributeElseAbsolute)) {
                                val expectedAltText = imageUriToAltText[relativeSrcAttributeElseAbsolute]!!
                                val actualAltAttribute = imageElement.getAttribute("alt")!!
                                if (expectedAltText != actualAltAttribute) {
                                    println(
                                        "${imageElement.getAttribute("outerHTML")} on $page has an " +
                                                "erroneous 'alt' attribute:\n'${expectedAltText}' (expected)\n" +
                                                "'${actualAltAttribute}' (actual)."
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
            assert(imageAltAttributesCorrect)
        }
}