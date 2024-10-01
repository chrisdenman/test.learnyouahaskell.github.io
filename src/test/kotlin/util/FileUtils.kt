package io.github.learnyouahaskell.test.util

import java.io.File

class FileUtils {
    companion object {
        const val NAME_EXTENSION_DELIMITER = "."
        fun dirExistsOrCreated(dir: File): Boolean = (dir.exists() || dir.mkdirs())
    }
}