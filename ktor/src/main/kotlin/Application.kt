package io.github.learnyouahaskell.test.ktor

import io.github.learnyouahaskell.test.config.loadFrom
import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.http.content.staticFiles
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import java.io.File
import java.lang.System.getProperty

val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))

fun main() {
    embeddedServer(
        Netty,
        port = configuration.serving.port.toInt(),
        host = configuration.serving.bindAddress, module = Application::module
    )
        .start(wait = true)
}

fun Application.module() {
    routing {
        staticFiles("/", configuration.serving.htmlContentRoot.file)
    }
}
