package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.assertLink
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.assertSize
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.hrefToUri
import io.github.learnyouahaskell.test.site.Chapters
import io.github.learnyouahaskell.test.site.Chapters.Companion.NAVIGATION__UP__TEXT
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
            val navChapters = chapters.navigation
            navChapters.forEachIndexed { index, capabilities ->

                get(navChapters[index].page.uri.toURL().toString())

                val isFirstChapter = index == 0
                val isLastChapter = index == navChapters.size - 1

                findElements(byClassName(Chapters.PREVIOUS_LINK_CLASS)).run {
                    assertSize(if (isFirstChapter) 0 else 2, this)
                    forEach { previousLink ->
                        navChapters[index - 1].run { assertLink(previousLink, page.uri, navigationLinkToText!!) }
                    }
                }

                findElements(byLinkText(NAVIGATION__UP__TEXT)).run {
                    assertSize(2, this)
                    forEach { it -> assertEquals(cs.hostedPages.chapters.uri, hrefToUri(it)) }
                }

                findElements(byClassName(Chapters.NEXT_LINK_CLASS)).run {
                    assertSize(if (isLastChapter) 0 else 2, this)
                    forEach { navChapters[index + 1].run { assertLink(it, page.uri, navigationLinkToText!!) } }
                }
            }
        }
}