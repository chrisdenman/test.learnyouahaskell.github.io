package io.github.learnyouahaskell.test.app

import io.github.learnyouahaskell.test.configuration.loadFrom
import io.github.learnyouahaskell.test.site.ExternalPages
import io.github.learnyouahaskell.test.site.HostedPages
import java.io.File
import java.lang.System.getProperty

object ConfigurationSupport {

    val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))

    val hostedPages: HostedPages = HostedPages(configuration)
    val externalPages: ExternalPages = ExternalPages(configuration)
    val browserNames: List<String> = configuration.tests.browsers.targets.map { it.name.toString() }
    val browserDimensions: List<Pair<Int, Int>> = configuration.tests.browsers.dimensions.map {
        it.width.toInt() to it.height.toInt()
    }
}