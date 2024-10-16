package io.github.learnyouahaskell.test.util

import java.net.URI

data class Image(val relativeUri: URI, val altText: String) {

    companion object {
        fun create(relativeUrl: String, altText: String): Image = Image(URI.create(relativeUrl), altText)
    }
}