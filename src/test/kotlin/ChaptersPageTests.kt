package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.Selenium.Companion.hrefToUri
import io.github.learnyouahaskell.test.Site.Companion.TITLE
import io.github.learnyouahaskell.test.config.loadFrom
import io.github.learnyouahaskell.test.util.UriUtils.Companion.withFragmentIdentifier
import org.openqa.selenium.By
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.remote.RemoteWebDriver
import java.io.File
import java.lang.System.getProperty
import kotlin.test.Test
import kotlin.test.assertEquals
import org.openqa.selenium.By.linkText as byLinkText
import org.openqa.selenium.By.xpath as byXPath

class ChaptersPageTests {

    private val configuration = loadFrom(File(getProperty("io.github.learnyouahaskell.test.config")))
    private val hostedPages: HostedPages = HostedPages(configuration)

    private val webDriver: RemoteWebDriver = RemoteWebDriver(
        configuration.tests.remoteWebDriver.uri.toURL(),
        FirefoxOptions()
    )

    private fun startAt(page: Page) = webDriver.get(page.uri.toString())
    private fun findElement(by: By): WebElement = webDriver.findElement(by)
    private fun stop() = webDriver.quit()

    fun pageSection(page: Page, title: String): Page = Page(withFragmentIdentifier(page.uri, title), page.name)

    private val linkTextToPageAddresses: Map<String, Page> = mapOf(
        "Introduction" to hostedPages.chapterIntroduction,
        "About this tutorial" to pageSection(hostedPages.chapterIntroduction, "about-this-tutorial"),
        "So what's Haskell?" to pageSection(hostedPages.chapterIntroduction, "so-whats-haskell"),
        "What you need to dive in" to pageSection(hostedPages.chapterIntroduction, "what-you-need"),

        "Starting Out" to hostedPages.chapterStartingOut,
        "Ready, set, go!" to pageSection(hostedPages.chapterStartingOut, "ready-set-go"),
        "Baby's first functions" to pageSection(hostedPages.chapterStartingOut, "babys-first-functions"),
        "An intro to lists" to pageSection(hostedPages.chapterStartingOut, "an-intro-to-lists"),
        "Texas ranges" to pageSection(hostedPages.chapterStartingOut, "texas-ranges"),
        "I'm a list comprehension" to pageSection(hostedPages.chapterStartingOut, "im-a-list-comprehension"),
        "Tuples" to pageSection(hostedPages.chapterStartingOut, "tuples"),

        "Types and Typeclasses" to hostedPages.chapterTypesAndTypeClasses,
        "Believe the type" to pageSection(hostedPages.chapterTypesAndTypeClasses, "believe-the-type"),
        "Type variables" to pageSection(hostedPages.chapterTypesAndTypeClasses, "type-variables"),
        "Typeclasses 101" to pageSection(hostedPages.chapterTypesAndTypeClasses, "typeclasses-101"),

        "Syntax in Functions" to hostedPages.chapterSyntaxInFunctions,
        "Pattern matching" to pageSection(hostedPages.chapterSyntaxInFunctions, "pattern-matching"),
        "Guards, guards!" to pageSection(hostedPages.chapterSyntaxInFunctions, "guards-guards"),
        "Where!?" to pageSection(hostedPages.chapterSyntaxInFunctions, "where"),
        "Let it be" to pageSection(hostedPages.chapterSyntaxInFunctions, "let-it-be"),
        "Case expressions" to pageSection(hostedPages.chapterSyntaxInFunctions, "case-expressions"),

        "Recursion" to hostedPages.chapterRecursion,
        "Hello recursion!" to pageSection(hostedPages.chapterRecursion, "hello-recursion"),
        "Maximum awesome" to pageSection(hostedPages.chapterRecursion, "maximum-awesome"),
        "A few more recursive functions" to pageSection(
            hostedPages.chapterRecursion,
            "a-few-more-recursive-functions"
        ),
        "Quick, sort!" to pageSection(hostedPages.chapterRecursion, "quick-sort"),
        "Thinking recursively" to pageSection(hostedPages.chapterRecursion, "thinking-recursively"),

        "Higher Order Functions" to hostedPages.chapterHigherOrderFunctions,
        "Curried functions" to pageSection(hostedPages.chapterHigherOrderFunctions, "curried-functions"),
        "Some higher-orderism is in order" to pageSection(
            hostedPages.chapterHigherOrderFunctions,
            "higher-orderism"
        ),
        "Maps and filters" to pageSection(hostedPages.chapterHigherOrderFunctions, "maps-and-filters"),
        "Lambdas" to pageSection(hostedPages.chapterHigherOrderFunctions, "lambdas"),
        "Only folds and horses" to pageSection(hostedPages.chapterHigherOrderFunctions, "folds"),
        "Function application with $" to pageSection(
            hostedPages.chapterHigherOrderFunctions,
            "function-application"
        ),
        "Function composition" to pageSection(hostedPages.chapterHigherOrderFunctions, "composition"),

        "Modules" to hostedPages.chapterModules,
        "Loading modules" to pageSection(hostedPages.chapterModules, "loading-modules"),
        "Data.List" to pageSection(hostedPages.chapterModules, "data-list"),
        "Data.Char" to pageSection(hostedPages.chapterModules, "data-char"),
        "Data.Map" to pageSection(hostedPages.chapterModules, "data-map"),
        "Data.Set" to pageSection(hostedPages.chapterModules, "data-set"),
        "Making our own modules" to pageSection(hostedPages.chapterModules, "making-our-own-modules"),

        "Making Our Own Types and Typeclasses" to hostedPages.chapterMakingOurOwnTypeclasses,
        "Algebraic data types intro" to pageSection(
            hostedPages.chapterMakingOurOwnTypeclasses,
            "algebraic-data-types"
        ),
        "Record syntax" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "record-syntax"),
        "Type parameters" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "type-parameters"),
        "Derived instances" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "derived-instances"),
        "Type synonyms" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "type-synonyms"),
        "Recursive data structures" to pageSection(
            hostedPages.chapterMakingOurOwnTypeclasses,
            "recursive-data-structures"
        ),
        "Typeclasses 102" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "typeclasses-102"),
        "A yes-no typeclass" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "a-yes-no-typeclass"),
        "The Functor typeclass" to pageSection(hostedPages.chapterMakingOurOwnTypeclasses, "the-functor-typeclass"),
        "Kinds and some type-foo" to pageSection(
            hostedPages.chapterMakingOurOwnTypeclasses,
            "kinds-and-some-type-foo"
        ),

        "Input and Output" to hostedPages.chapterInputAndOutput,
        "Hello, world!" to pageSection(hostedPages.chapterInputAndOutput, "hello-world"),
        "Files and streams" to pageSection(hostedPages.chapterInputAndOutput, "files-and-streams"),
        "Command line arguments" to pageSection(hostedPages.chapterInputAndOutput, "command-line-arguments"),
        "Randomness" to pageSection(hostedPages.chapterInputAndOutput, "randomness"),
        "Bytestrings" to pageSection(hostedPages.chapterInputAndOutput, "bytestrings"),
        "Exceptions" to pageSection(hostedPages.chapterInputAndOutput, "exceptions"),

        "Functionally Solving Problems" to hostedPages.chapterFunctionallySolvingProblems,
        "Reverse Polish notation calculator" to pageSection(
            hostedPages.chapterFunctionallySolvingProblems,
            "reverse-polish-notation-calculator"
        ),
        "Heathrow to London" to pageSection(hostedPages.chapterFunctionallySolvingProblems, "heathrow-to-london"),

        "Functors, Applicative Functors and Monoids" to hostedPages.chapterFunctionsApplicativeFunctorsAndMonoids,
        "Functors redux" to pageSection(
            hostedPages.chapterFunctionsApplicativeFunctorsAndMonoids,
            "functors-redux"
        ),
        "Applicative functors" to pageSection(
            hostedPages.chapterFunctionsApplicativeFunctorsAndMonoids,
            "applicative-functors"
        ),
        "The newtype keyword" to pageSection(
            hostedPages.chapterFunctionsApplicativeFunctorsAndMonoids,
            "the-newtype-keyword"
        ),
        "Monoids" to pageSection(hostedPages.chapterFunctionsApplicativeFunctorsAndMonoids, "monoids"),

        "A Fistful of Monads" to hostedPages.chapterAFistfulOfMonads,
        "Getting our feet wet with Maybe" to pageSection(
            hostedPages.chapterAFistfulOfMonads,
            "getting-our-feet-wet-with-maybe"
        ),
        "The Monad type class" to pageSection(hostedPages.chapterAFistfulOfMonads, "the-monad-type-class"),
        "Walk the line" to pageSection(hostedPages.chapterAFistfulOfMonads, "walk-the-line"),
        "do notation" to pageSection(hostedPages.chapterAFistfulOfMonads, "do-notation"),
        "The list monad" to pageSection(hostedPages.chapterAFistfulOfMonads, "the-list-monad"),
        "Monad laws" to pageSection(hostedPages.chapterAFistfulOfMonads, "monad-laws"),

        "For a Few Monads More" to hostedPages.chapterForAFewMonadsMore,
        "Writer? I hardly know her!" to pageSection(hostedPages.chapterForAFewMonadsMore, "writer"),
        "Reader? Ugh, not this joke again." to pageSection(hostedPages.chapterForAFewMonadsMore, "reader"),
        "Tasteful stateful computations" to pageSection(hostedPages.chapterForAFewMonadsMore, "state"),
        "Error error on the wall" to pageSection(hostedPages.chapterForAFewMonadsMore, "error"),
        "Some useful monadic functions" to pageSection(
            hostedPages.chapterForAFewMonadsMore,
            "useful-monadic-functions"
        ),
        "Making monads" to pageSection(hostedPages.chapterForAFewMonadsMore, "making-monads"),

        "Zippers" to hostedPages.chapterZippers,
        "Taking a walk" to pageSection(hostedPages.chapterZippers, "taking-a-walk"),
        "A trail of breadcrumbs" to pageSection(hostedPages.chapterZippers, "a-trail-of-breadcrumbs"),
        "Focusing on lists" to pageSection(hostedPages.chapterZippers, "focusing-on-lists"),
        "A very simple file system" to pageSection(hostedPages.chapterZippers, "a-very-simple-file-system"),
        "Watch your step" to pageSection(hostedPages.chapterZippers, "watch-your-step"),
    )

    @Test
    fun `That the title is correct`() = AutoCloseable { stop() }.use {
        startAt(hostedPages.chapters)
        assertEquals("Chapters - $TITLE", webDriver.title)
    }

    @Test
    fun `That the header contains the correct value`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.chapters)
            assertEquals(TITLE, findElement(byXPath("//div[@id='content']/h1")).text)
        }

    @Test
    fun `That the chapter links contain the correct text and addresses`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.chapters)
            linkTextToPageAddresses.forEach {
                assertEquals(
                    it.value.uri,
                    hrefToUri(findElement(byLinkText(it.key))),
                    "That the link with text \"${it.key}\" has an address of \"${it.value}\""
                )
            }
        }

    @Test
    @Suppress("SpellCheckingInspection")
    fun `That the license information contains the correct text`() =
        AutoCloseable { stop() }.use {
            startAt(hostedPages.chapters)
            assertEquals(
                "This work is licensed under a Creative Commons Attribution-Noncommercial-Share Alike 3.0 Unported " +
                        "License because I couldn't find a license with an even longer name.",
                findElement(byXPath("//p[last()]")).text
            )
        }
}