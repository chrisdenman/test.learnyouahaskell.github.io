package io.github.learnyouahaskell.test.util

import java.net.URI

class UriUtils {
    companion object {
        fun withFragmentIdentifier(uri: URI, fragment: String = ""): URI =
            uri.run { URI(scheme, userInfo, host, port, path, query, fragment) }
    }
}