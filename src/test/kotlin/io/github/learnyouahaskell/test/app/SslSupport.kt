package io.github.learnyouahaskell.test.app

import java.io.FileInputStream
import java.lang.System.getProperty
import java.security.KeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object SslSupport {

    val cs = ConfigurationSupport

    val keyStorePassword = getProperty("io.github.learnyouahaskell.test.keyStorePassword")

    val keyStore: KeyStore =
        KeyStore
            .getInstance(KeyStore.getDefaultType())
            .apply {
                load(
                    FileInputStream(cs.configuration.serving.tls.keyStoreFilename),
                    keyStorePassword.toCharArray()
                )
            }

    val trustManagerFactory: TrustManagerFactory? =
        TrustManagerFactory
            .getInstance(TrustManagerFactory.getDefaultAlgorithm())
            .apply {
                init(keyStore)
            }

    val sslContext: SSLContext? =                   
        SSLContext
            .getInstance("TLS")
            .apply {
                init(null, trustManagerFactory?.trustManagers, null)
            }
}