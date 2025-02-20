package framework

import java.net.URI
import java.nio.file.Files
import java.nio.file.Path

internal fun resourceAsText(fileName: String): String =
    Files.readString(Path.of(fileName.toURI()))

private fun String.toURI(): URI =
    Test::class.java.getResource(this)?.toURI()
        ?: throw IllegalArgumentException("Cannot find resource: $this")