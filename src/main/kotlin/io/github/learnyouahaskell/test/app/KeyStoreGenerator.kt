package io.github.learnyouahaskell.test.app

import io.ktor.network.tls.certificates.buildKeyStore
import io.ktor.network.tls.certificates.saveToFile
import io.ktor.network.tls.extensions.HashAlgorithm
import java.io.File
import java.lang.System.getProperty
import javax.security.auth.x500.X500Principal
import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule

object KeyStoreGenerator {

    fun generateKeyStore(
        keyStorePassword: String,
        privateKeyPassword: String,
        configJsonFilename: String
    ) {
        loadFrom(File(configJsonFilename)).let {
            val certificateAlias = it.certificateAlias
            val keyStoreFile = File(it.filename)
            buildKeyStore {
                certificate(certificateAlias) {
                    subject = X500Principal( it.subject)
                    daysValid = it.daysValid.toInt().toLong()
                    hash = HashAlgorithm.SHA256
                    keySizeInBits = it.keySizeInBits.toInt()
                    password = privateKeyPassword
                    domains = it.domains
                }
            }
                .saveToFile(keyStoreFile, keyStorePassword)
        }
    }
}

fun main() {
    val keyStorePassword = getProperty("io.github.learnyouahaskell.test.keyStorePassword")
    val privateKeyPassword = getProperty("io.github.learnyouahaskell.test.privateKeyPassword")
    val configFilename = getProperty("io.github.learnyouahaskell.test.config")
    KeyStoreGenerator.generateKeyStore(keyStorePassword, privateKeyPassword, configFilename)
}

fun loadFrom(file: File): KeyStoreConfig = json.decodeFromString<KeyStoreConfig>(file.readText())

val json = Json {
    serializersModule = SerializersModule {
        contextual(PositiveInt::class, PositiveIntSerializer)
    }
}

@Serializable
data class KeyStoreConfig(
    @SerialName("certificate-alias") val certificateAlias: String = "test.lyah",
    val filename: String = "lyah.jks",
    @Contextual @SerialName("days-valid") val daysValid: PositiveInt = PositiveInt(36500),
    @Contextual @SerialName("key-size-bits") val keySizeInBits: PositiveInt = PositiveInt(4096),
    val subject: String = "CN=test.learnyouahaskell.github.io, OU=learnyouahaskell, O=ceilingcat.co.uk, C=UK",
    val domains: List<String> = listOf("test.learnyouahaskell.github.io")
)

object PositiveIntSerializer : KSerializer<PositiveInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PositiveInt", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: PositiveInt) = encoder.encodeInt(value.toInt())
    override fun deserialize(decoder: Decoder): PositiveInt = PositiveInt(decoder.decodeInt())
}

data class PositiveInt(val value: Int) {
    init {
        require(value >= 0) { "Values must be positive." }
    }

    fun toInt() = value
    override fun toString(): String = value.toString()
}
