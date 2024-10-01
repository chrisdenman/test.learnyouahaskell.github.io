package io.github.learnyouahaskell.test

import org.openqa.selenium.WebElement
import java.net.URI.create as createUri

class Selenium {
    companion object {
        fun hrefToUri(element: WebElement) = createUri(element.getAttribute("href")!!)!!
    }
}