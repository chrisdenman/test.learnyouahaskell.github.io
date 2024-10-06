import java.net.URI.create as createUri

class ExternalImages {
    @Suppress("HttpUrlsUsage")
    val thisExpression = Image(createUri("http://s3.amazonaws.com/lyah/rpn.png"), "this expression")

    val all: List<Image> = listOf(thisExpression)
}
