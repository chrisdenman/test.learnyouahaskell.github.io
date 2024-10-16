package io.github.learnyouahaskell.test.configuration

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
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

val json = Json {
    serializersModule = SerializersModule {
        contextual(DateTimeFormatter::class, DateTimeFormatterSerializer)
        contextual(File::class, FileAsStringSerializer)
        contextual(ExtantDirectory::class, ExtantDirectorySerializer)
        contextual(HttpScheme::class, HttpSchemeSerializer)
        contextual(ImageFormat::class, ImageFormatSerializer)
        contextual(NonNegativeInt::class, NonNegativeIntSerializer)
        contextual(PositiveInt::class, PositiveIntSerializer)
        contextual(SeleniumBrowserStereotype::class, SeleniumBrowserStereotypeSerializer)
        contextual(URI::class, URISerializer)
    }
}

fun loadFrom(file: File): Configuration = json.decodeFromString<Configuration>(file.readText()).also { validate(it) }

fun interpolate(outputFileTemplate: String, properties: FilenameTemplateProperties): String =
    properties
        .get()
        .entries
        .fold(outputFileTemplate) { acc, (k, v) ->
            acc.replace("{{${k}}}", v.toString())
        }

fun validate(configuration: Configuration) {
    val outputFilename = interpolate(
        configuration.tests.screenshots.outputFileTemplate,
        FilenameTemplateProperties(configuration).apply {
            addStartTimeUtc(LocalDateTime.now())
            addRelativePageUrl("relativePageUrl")
            addBrowserProperties(
                "browserTarget",
                1024,
                800,
                0,
                100,
                "129_0_12.1"
            )
            addPlatformProperties(
                "platformName",
                100,
                0
            )
            addTileIndex(0)
        }
    )

    Regex("(\\{\\{)(.*?)}}").findAll(outputFilename).toList().run {
        if (this.isNotEmpty()) {
            throw IllegalStateException(
                "tests.screenshots.output_file_template contains unknown tokens: ${
                    this.map { it.value }.joinToString()
                }"
            )
        }
    }
}

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

object FileAsStringSerializer : KSerializer<File> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("kotlinx.serialization.FileAsStringSerializer", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: File) {
        encoder.encodeString(value.toString())
    }

    override fun deserialize(decoder: Decoder): File {
        return File(decoder.decodeString())
    }
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

    override fun serialize(encoder: Encoder, value: SeleniumBrowserStereotype) =
        encoder.encodeString(value.toString())
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
) {
    @Suppress("HttpUrlsUsage", "unused")
    @Transient val httpUri: URI = URI.create("http://test.learnyouahaskell.github.io:${serving.port}")
    @Transient val httpsUri: URI = URI.create("https://test.learnyouahaskell.github.io:${serving.tls.port}")
}

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
    @Contextual val port: NonNegativeInt,
    @SerialName("html-content-root") @Contextual val htmlContentRoot: ExtantDirectory,
    @Contextual val tls: TLS
)

@Serializable
data class TLS(
    @Contextual @SerialName("certificate-alias") val certificateAlias: String = "test.lyah",
    @Contextual @SerialName("keyStore-filename") val keyStoreFilename: File,
    @Contextual val port: NonNegativeInt,
)

class FilenameTemplateProperties(val configuration: Configuration) {
    private fun addJavaSystemProperties(): FilenameTemplateProperties =
        this.apply {
            System.getProperties().forEach { (key, value) ->
                properties.put(key.toString(), value)
            }
        }

    private fun addEnvironmentVariables() =
        this.apply {
            properties.putAll(System.getenv())
        }

    private val properties: MutableMap<String, Any> = mutableMapOf<String, Any>()

    init {
        addJavaSystemProperties()
            .addJavaSystemProperties()
            .addEnvironmentVariables()
            .addConfigurationProperties(configuration)
    }

    fun addStartTimeUtc(now: LocalDateTime) {
        properties["start_time_utc"] = now
            .atOffset(ZoneOffset.UTC)
            .format(configuration.tests.screenshots.dateTimeFormatter)
    }

    fun addRelativePageUrl(url: String) {
        properties["relative_page_url"] = if (url.isEmpty()) "ROOT" else url
    }

    fun addTileIndex(index: Int) {
        properties["tile.index"] = index
    }

    fun addBrowserProperties(name: String, width: Int, height: Int, x: Int, y: Int, browserVersion: String) {
        properties["browser.name"] = name
        properties["browser.width"] = width.toInt()
        properties["browser.height"] = height.toInt()
        properties["browser.x"] = x
        properties["browser.y"] = y
        properties["browser.version"] = browserVersion
    }

    fun addPlatformProperties(name: String, majorVersion: Int, minorVersion: Int) {
        properties["platform.name"] = name
        properties["platform.major_version"] = majorVersion.toInt()
        properties["platform.minor_version"] = minorVersion.toInt()
    }

    fun addConfigurationProperties(configuration: Configuration) {
        configuration.run {
            properties.putAll(
                listOf<Pair<String, Any>>(
                    "id" to id,
                    "github.owner" to github.owner,
                    "github.repository" to github.repository,
                    "github.html_content_root" to github.htmlContentRoot,
                    "github.branch" to github.branch,
                    "github.sha" to github.sha,
                    "screenshots.maximum_height_pixels" to tests.screenshots.maximumHeightPixels.toInt(),
                    "screenshots.image_format" to tests.screenshots.imageFormat.toString()
                )
            )
        }
    }

    fun get(): Map<String, Any> = properties.toMap()
}