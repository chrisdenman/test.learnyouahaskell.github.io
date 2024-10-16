package io.github.learnyouahaskell.test.util

import java.awt.image.BufferedImage
import kotlin.math.min

class BufferedImageUtils() {
    companion object {
        fun getVerticalTiles(bufferedImage: BufferedImage, maxHeight: Int): List<BufferedImage> {
            val tiles = mutableListOf<BufferedImage>()
            val imageHeight = bufferedImage.height
            var tileYStart = 0
            while (tileYStart < imageHeight) {
                val tileYEnd = min(tileYStart + maxHeight - 1, imageHeight - 1)
                tiles.add(bufferedImage
                    .getSubimage(0, tileYStart, bufferedImage.width, tileYEnd - tileYStart + 1)!!)
                tileYStart = tileYEnd + 1
            }

            return tiles
        }
    }
}