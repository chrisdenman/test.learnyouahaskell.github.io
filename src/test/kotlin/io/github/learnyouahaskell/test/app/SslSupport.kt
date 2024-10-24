package io.github.learnyouahaskell.test.app

import io.github.learnyouahaskell.test.app.ConfigurationSupport.config
import java.io.FileInputStream
import java.lang.System.getProperty
import java.security.KeyStore
import java.security.KeyStore.getDefaultType as getDefaultKeyStoreType
import java.security.KeyStore.getInstance as getKeyStore
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

object SslSupport {

    val keyStore: KeyStore =
        getKeyStore(getDefaultKeyStoreType())
            .apply {
                load(
                    FileInputStream(config.serving.tls.keyStoreFile),
                    getProperty("io.github.learnyouahaskell.test.keyStorePassword").toCharArray()
                )
            }

    val trustManagerFactory: TrustManagerFactory =
        TrustManagerFactory
            .getInstance(TrustManagerFactory.getDefaultAlgorithm())
            .apply {
                init(keyStore)
            }

    val sslContext: SSLContext =
        SSLContext
            .getInstance("TLS")
            .apply {
                init(null, trustManagerFactory.trustManagers, null)
            }
}