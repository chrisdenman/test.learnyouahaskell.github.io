package io.github.learnyouahaskell.test.app

import io.ktor.network.tls.certificates.buildKeyStore
import io.ktor.network.tls.certificates.saveToFile
import io.ktor.network.tls.extensions.HashAlgorithm
import java.lang.System.getProperty
import javax.security.auth.x500.X500Principal

object KeyStoreSupport {

    fun generateKeyStore(keyStorePassword: String, privateKeyPassword: String) {
        ConfigurationSupport
            .configuration
            .serving
            .let { servingConfiguration ->
                val certificateAlias = servingConfiguration.tls.certificateAlias
                val keyStoreFile = servingConfiguration.tls.keyStoreFilename
                buildKeyStore {
                    certificate(certificateAlias) {
                        subject = X500Principal("CN=test.learnyouahaskell.github.io, OU=learnyouahaskell, O=ceilingcat.co.uk, C=UK")
                        daysValid = 1
                        hash = HashAlgorithm.SHA256
                        keySizeInBits = 4096
                        password = privateKeyPassword
                        domains = listOf("test.learnyouahaskell.github.io")
                    }
                }
                    .saveToFile(keyStoreFile, keyStorePassword)
            }
    }
}

fun main() {
    val keyStorePassword = getProperty("io.github.learnyouahaskell.test.keyStorePassword")
    val privateKeyPassword = getProperty("io.github.learnyouahaskell.test.privateKeyPassword")
    KeyStoreSupport.generateKeyStore(keyStorePassword, privateKeyPassword)
}