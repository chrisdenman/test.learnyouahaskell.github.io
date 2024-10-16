package io.github.learnyouahaskell.test.app

import io.github.learnyouahaskell.test.configuration.loadFrom
import io.ktor.server.application.Application
import io.ktor.server.engine.applicationEngineEnvironment
import io.ktor.server.engine.connector
import io.ktor.server.engine.embeddedServer
import io.ktor.server.engine.sslConnector
import io.ktor.server.http.content.staticFiles
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import java.io.File
import java.lang.System.getProperty
import java.security.KeyStore

val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))

fun main() {

    val keyStorePassword = getProperty("io.github.learnyouahaskell.test.keyStorePassword")
    val privateKeyPassword = getProperty("io.github.learnyouahaskell.test.privateKeyPassword")
    val keyStoreFile = getProperty("io.github.learnyouahaskell.test.keyStore")
    val hostDnsName = "test.learnyouahaskell.github.io"

    configuration.serving.let { servingConfiguration ->
        val keyStore = KeyStore.getInstance(File(keyStoreFile), keyStorePassword.toCharArray())!!

        val environment = applicationEngineEnvironment {
            connector {
                host = hostDnsName
                port = servingConfiguration.port.toInt()
            }
            sslConnector(
                keyStore = keyStore,
                keyAlias = servingConfiguration.tls.certificateAlias,
                keyStorePassword = { keyStorePassword.toCharArray() },
                privateKeyPassword = { privateKeyPassword.toCharArray() }) {
                host = hostDnsName
                port = servingConfiguration.tls.port.toInt()
            }
            module(Application::module)
        }

        embeddedServer(Netty, environment).start(wait = true)
    }
}

fun Application.module() {
    routing {
        staticFiles("/", configuration.serving.htmlContentRoot.file)
    }
}
