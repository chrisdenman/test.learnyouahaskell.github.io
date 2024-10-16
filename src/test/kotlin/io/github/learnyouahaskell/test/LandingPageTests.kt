package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.hrefToUri
import io.github.learnyouahaskell.test.site.ExternalUris.Companion.BOOK
import io.github.learnyouahaskell.test.site.ExternalUris.Companion.PULL_REQUEST
import io.github.learnyouahaskell.test.site.Site.Companion.TITLE
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.openqa.selenium.By.id as byId
import org.openqa.selenium.By.linkText as byLinkText

class LandingPageTests {

    val cs = ConfigurationSupport
    val ss = SeleniumSupport()

    @Test
    fun `That the title is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals("$TITLE (up-to-date)", title)
        }

    @Test
    fun `That the link to purchasing the physical book is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals(BOOK, hrefToUri(findElement(byId("book-button"))))
        }

    @Test
    fun `That the link to purchasing the physical book has the correct text`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals("Buy it!", findElement(byId("book-button")).text)
        }

    @Test
    fun `That the FAQ link text is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals("Got questions? Read the FAQ", findElement(byId("faq-button")).text)
        }

    @Test
    fun `That the link to the FAQ page is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals(cs.hostedPages.faq.uri, hrefToUri(findElement(byId("faq-button"))))
        }

    @Test
    fun `That the link to the chapters page is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals(cs.hostedPages.chapters.uri, hrefToUri(findElement(byId("read-button"))))
        }

    @Test
    fun `That the link to the chapters page has the correct text`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals("Read it online!", findElement(byId("read-button")).text)
        }

    @Test
    fun `That the link for opening a pull request is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals(PULL_REQUEST, hrefToUri(findElement(byLinkText("opening a pull request"))))
        }

    @Test
    fun `That the link for creating a content edit request is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.landing.uriAsText)
            assertEquals(
                cs.externalPages.createContentEditRequest, hrefToUri(findElement(byLinkText("content edit request")))
            )
        }
}