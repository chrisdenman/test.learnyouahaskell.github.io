package io.github.learnyouahaskell.test.util

import java.io.File

class FileUtils {
    companion object {
        fun dirExistsOrCreated(dir: File): Boolean = (dir.exists() || dir.mkdirs())
    }
}