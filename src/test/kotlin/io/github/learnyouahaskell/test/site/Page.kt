package io.github.learnyouahaskell.test.site

import java.net.URI

data class Page(val uri: URI, val name: String) {
    fun resolve(child: String, name: String): Page = Page(uri.resolve(child), name)
    val uriAsText = uri.toString()
}