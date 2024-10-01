package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.ExternalUris.Companion.BOOK
import io.github.learnyouahaskell.test.ExternalUris.Companion.PULL_REQUEST
import io.github.learnyouahaskell.test.Selenium.Companion.hrefToUri
import io.github.learnyouahaskell.test.Site.Companion.TITLE
import io.github.learnyouahaskell.test.config.loadFrom
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.lang.System.getProperty
import kotlin.test.assertEquals
import org.openqa.selenium.By.id as byId
import org.openqa.selenium.By.linkText as byLinkText

class LandingPageTests {

    private val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))
    private val hostedPages: HostedPages = HostedPages(configuration)

    private val webDriver: RemoteWebDriver = RemoteWebDriver(
        configuration.tests.remoteWebDriver.uri.toURL(),
        FirefoxOptions()
    )

    private fun startAt(page: Page) = webDriver.get(page.uri.toString())
    private fun stop() = webDriver.quit()
    private fun findElement(by: By): WebElement = webDriver.findElement(by)

    @Test
    fun `That the title is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals("$TITLE (up-to-date)", webDriver.title)
        }

    @Test
    fun `That the link to purchasing the physical book is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals(BOOK, hrefToUri(findElement(byId("book-button"))))
        }

    @Test
    fun `That the link to purchasing the physical book has the correct text`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals("Buy it!", findElement(byId("book-button")).text)
        }

    @Test
    fun `That the FAQ link text is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals("Got questions? Read the FAQ", findElement(byId("faq-button")).text)
        }

    @Test
    fun `That the link to the FAQ page is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals(hostedPages.faq.uri, hrefToUri(findElement(byId("faq-button"))))
        }

    @Test
    fun `That the link to the chapters page is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals(
                hostedPages.chapters.uri,
                hrefToUri(findElement(byId("read-button")))
            )
        }

    @Test
    fun `That the link to the chapters page has the correct text`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals("Read it online!", findElement(byId("read-button")).text)
        }

    @Test
    fun `That the link for opening a pull request is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals(
                PULL_REQUEST,
                hrefToUri(findElement(byLinkText("opening a pull request")))
            )
        }

    @Test
    fun `That the link for creating a content edit request is correct`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.landing)
            assertEquals(
                hostedPages.createContentEditRequest,
                hrefToUri(findElement(byLinkText("content edit request")))
            )
        }
}