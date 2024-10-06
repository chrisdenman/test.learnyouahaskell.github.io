import io.github.learnyouahaskell.test.config.Configuration
import java.net.URI.create as createUri

class ExternalPages(configuration: Configuration) {
    val createContentEditRequest =
        createUri("https://github.com/learnyouahaskell/learnyouahaskell.github.io/issues/new/choose")
}