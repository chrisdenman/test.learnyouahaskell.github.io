package io.github.learnyouahaskell.test

import java.net.URI
import java.net.URI.create as createUri

class ExternalUris {
    companion object {
        val BOOK: URI = createUri("https://nostarch.com/lyah.htm")
        val PULL_REQUEST = createUri("https://github.com/learnyouahaskell/learnyouahaskell.github.io")!!
    }
}