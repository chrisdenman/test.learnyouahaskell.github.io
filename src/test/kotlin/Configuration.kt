package io.github.learnyouahaskell.test.config

import kotlinx.serialization.Contextual
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import java.io.File
import java.net.URI
import java.time.format.DateTimeFormatter
import java.util.Locale

val json = Json {
    serializersModule = SerializersModule {
        contextual(DateTimeFormatter::class, DateTimeFormatterSerializer)
        contextual(ExtantDirectory::class, ExtantDirectorySerializer)
        contextual(HttpScheme::class, HttpSchemeSerializer)
        contextual(ImageFormat::class, ImageFormatSerializer)
        contextual(NonNegativeInt::class, NonNegativeIntSerializer)
        contextual(PositiveInt::class, PositiveIntSerializer)
        contextual(SeleniumBrowserStereotype::class, SeleniumBrowserStereotypeSerializer)
        contextual(URI::class, URISerializer)
    }
}

fun loadFrom(file: File): Configuration = json.decodeFromString(file.readText())

object DateTimeFormatterSerializer : KSerializer<DateTimeFormatter> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DateTimeFormatter", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: DateTimeFormatter) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): DateTimeFormatter =
        DateTimeFormatter.ofPattern(decoder.decodeString(), Locale.getDefault())
}

object ExtantDirectorySerializer : KSerializer<ExtantDirectory> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ExtantDirectory", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: ExtantDirectory) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): ExtantDirectory = ExtantDirectory(decoder.decodeString())
}

object HttpSchemeSerializer : KSerializer<HttpScheme> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("HttpScheme", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: HttpScheme) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): HttpScheme = HttpScheme(decoder.decodeString())
}

object ImageFormatSerializer : KSerializer<ImageFormat> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ImageFormat", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: ImageFormat) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): ImageFormat = ImageFormat(decoder.decodeString())
}

object NonNegativeIntSerializer : KSerializer<NonNegativeInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("NonNegativeInt", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: NonNegativeInt) = encoder.encodeInt(value.toInt())
    override fun deserialize(decoder: Decoder): NonNegativeInt = NonNegativeInt(decoder.decodeInt())
}

object PositiveIntSerializer : KSerializer<PositiveInt> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("PositiveInt", PrimitiveKind.INT)
    override fun serialize(encoder: Encoder, value: PositiveInt) = encoder.encodeInt(value.toInt())
    override fun deserialize(decoder: Decoder): PositiveInt = PositiveInt(decoder.decodeInt())
}

object SeleniumBrowserStereotypeSerializer : KSerializer<SeleniumBrowserStereotype> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("SeleniumBrowserStereotype", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: SeleniumBrowserStereotype) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): SeleniumBrowserStereotype =
        SeleniumBrowserStereotype(decoder.decodeString())
}

object URISerializer : KSerializer<URI> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("URI", PrimitiveKind.STRING)
    override fun serialize(encoder: Encoder, value: URI) = encoder.encodeString(value.toString())
    override fun deserialize(decoder: Decoder): URI = URI.create(decoder.decodeString())
}

data class HttpScheme(val value: String) {
    init {
        require(value in listOf<String>("http", "https")) {
            "Only: \"http\" or \"https\" are supported."
        }
    }

    override fun toString(): String = value.toString()
}

data class NonNegativeInt(val value: Int) {
    init {
        require(value >= 0) { "Values must be non-negative." }
    }

    fun toInt() = value
    override fun toString(): String = value.toString()
}

data class PositiveInt(val value: Int) {
    init {
        require(value >= 0) { "Values must be positive." }
    }

    fun toInt() = value
    override fun toString(): String = value.toString()
}

data class SeleniumBrowserStereotype(val value: String) {
    init {
        require(value in listOf<String>("firefox", "chrome", "edge")) {
            "Only: \"firefox\", \"chrome\" or \"edge\" are supported."
        }
    }

    override fun toString(): String = value.toString()
}

data class ExtantDirectory(private val value: String, val file: File = File(value)) {
    init {
        file.exists() && file.isDirectory
    }
}

data class ImageFormat(val value: String) {
    init {
        require(value in listOf<String>("png")) {
            "Only: \"png\" is supported."
        }
    }

    override fun toString(): String = value.toString()
}

@Serializable
data class Browsers(
    val targets: Set<BrowserTarget>,
    val dimensions: Set<Dimension>
)

@Serializable
data class BrowserTarget(
    @Contextual val name: SeleniumBrowserStereotype
)

@Serializable
data class Dimension(
    @Contextual val width: PositiveInt,
    @Contextual val height: PositiveInt
)

@Serializable
data class Configuration(
    val id: String,
    val github: GitHub,
    val tests: Tests,
    val serving: Serving
)

@Serializable
data class GitHub(
    val owner: String,
    val repository: String,
    val branch: String,
    val sha: String,
    @SerialName("html_content_root") val htmlContentRoot: String
)

@Serializable
data class RemoteWebDriver(
    @Contextual val scheme: HttpScheme,
    @SerialName("bind-address") val bindAddress: String,
    @Contextual val port: NonNegativeInt,
    @Transient val uri: URI = URI.create("$scheme://$bindAddress:$port")
) {
    override fun toString(): String = uri.toString()
}

@Serializable
data class ScreenShots(
    @SerialName("output_file_template") val outputFileTemplate: String,
    @Contextual @SerialName("dateTime_formatter_pattern") val dateTimeFormatter: DateTimeFormatter,
    @Contextual @SerialName("maximum_height_pixels") val maximumHeightPixels: PositiveInt,
    @Contextual @SerialName("scroll_timeout_milliseconds") val scrollTimeoutMilliseconds: PositiveInt,
    @Contextual @SerialName("image_format") val imageFormat: ImageFormat
)

@Serializable
data class Tests(
    @SerialName("relative_page_urls") val relativePageUrls: List<String>,  // @todo should be list of relative URIs
    val screenshots: ScreenShots,
    val browsers: Browsers,
    @SerialName("remote-web-driver") val remoteWebDriver: RemoteWebDriver
)

@Serializable
data class Serving(
    @Contextual val scheme: HttpScheme,
    @SerialName("bind-address") val bindAddress: String,
    @Contextual val port: NonNegativeInt,
    @SerialName("html-content-root") @Contextual val htmlContentRoot: ExtantDirectory,
    @Transient val uri: URI = URI.create("$scheme://$bindAddress:$port")
)