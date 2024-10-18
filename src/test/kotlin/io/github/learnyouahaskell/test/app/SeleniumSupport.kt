package io.github.learnyouahaskell.test.app

import org.openqa.selenium.Capabilities
import org.openqa.selenium.Dimension
import org.openqa.selenium.MutableCapabilities
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.edge.EdgeOptions
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.AbstractDriverOptions
import org.openqa.selenium.remote.Browser
import org.openqa.selenium.remote.RemoteWebDriver
import kotlin.reflect.KClass
import java.net.URI.create as createUri

class SeleniumSupport {

    val browserToCapabilities: Map<Browser, KClass<out MutableCapabilities>> =
        mapOf(
            Browser.FIREFOX to FirefoxOptions::class,
            Browser.CHROME to ChromeOptions::class,
            Browser.EDGE to EdgeOptions::class
        )

    val browserNameToBrowser: Map<String, Browser> =
        mapOf(
            "firefox" to Browser.FIREFOX,
            "chrome" to Browser.CHROME,
            "edge" to Browser.EDGE
        )

    fun browserDimensionsToDimension(dimensions: Pair<Int, Int>): Dimension =
        Dimension(dimensions.first, dimensions.second)

    fun drive(
        browsers: List<Browser>,
        browserDimensions: List<Dimension>,
        code: RemoteWebDriver.(Capabilities, Browser, Dimension) -> Unit
    ) =
        browsers.forEach { browser ->


            RemoteWebDriver(
                (browserToCapabilities[browser]!!
                    .constructors
                    .first()
                    .call() as AbstractDriverOptions<*>)
                    .setAcceptInsecureCerts(true),
                IS_WEBDRIVER_TRACING_ENABLED
            ).run {
                AutoCloseable { quit() }.use {
                    browserDimensions.forEach { dimensions ->
                        manage().window().size = dimensions
                        this.code(this.capabilities, browser, dimensions)
                    }
                }
            }
        }

    companion object {
        const val IS_WEBDRIVER_TRACING_ENABLED = false
        fun hrefToUri(element: WebElement) = createUri(element.getAttribute("href")!!)!!

        fun isAttributeDeclared(element: WebElement, attributeName: String): Boolean =
            element.getAttribute(attributeName).let {
                it != null && it.isNotEmpty() && it.isNotBlank()
            }

        fun isPositiveIntAttribute(element: WebElement, attributeName: String): Boolean =
            element.getAttribute(attributeName).let {
                isAttributeDeclared(element, attributeName) && it!!.toIntOrNull() != null
            }
    }
}