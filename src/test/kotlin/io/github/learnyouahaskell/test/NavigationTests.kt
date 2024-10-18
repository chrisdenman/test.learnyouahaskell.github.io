package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.hrefToUri
import io.github.learnyouahaskell.test.site.Chapters
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import org.openqa.selenium.By.className as byClassName
import org.openqa.selenium.By.linkText as byLinkText

class NavigationTests {

    val cs = ConfigurationSupport
    val ss = SeleniumSupport()
    val chapters = Chapters(cs.hostedPages)

    @Test
    fun `That the navigation links between chapters are correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            val nav = chapters.navigation
            nav.forEachIndexed { index, capabilities ->

                get(nav[index].page.uri.toURL().toString())

                val isFirstChapter = index == 0
                val isLastChapter = index == nav.size - 1

                if (!isFirstChapter) {
                    findElements(byClassName(Chapters.PREVIOUS_LINK_CLASS)).also {
                        assertEquals(2, it.size)
                    }.forEach {
                        assertEquals(nav[index - 1].page.uri, hrefToUri(it))
                        assertEquals(nav[index - 1].navigationLinkToText, it.text)
                    }

                }

                if (!isLastChapter) {
                    findElements(byClassName(Chapters.NEXT_LINK_CLASS)).also {
                        assertEquals(2, it.size)
                    }.forEach { it ->
                        assertEquals(nav[index + 1].page.uri, hrefToUri(it))
                        assertEquals(nav[index + 1].navigationLinkToText, it.text)
                    }
                }

                findElements(byLinkText("Table of contents")).also {
                    assertEquals(2, it.size)
                }.forEach { it->
                    assertEquals(cs.hostedPages.chapters.uri, hrefToUri(it))
                }
            }
        }
}