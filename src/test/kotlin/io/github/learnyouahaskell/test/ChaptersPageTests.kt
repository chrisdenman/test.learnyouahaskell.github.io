package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.app.ConfigurationSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport
import io.github.learnyouahaskell.test.app.SeleniumSupport.Companion.hrefToUri
import io.github.learnyouahaskell.test.site.Page
import io.github.learnyouahaskell.test.site.Site.Companion.TITLE
import io.github.learnyouahaskell.test.util.UriUtils.Companion.withFragmentIdentifier
import kotlin.test.Test
import kotlin.test.assertEquals
import org.openqa.selenium.By.linkText as byLinkText
import org.openqa.selenium.By.xpath as byXPath

class ChaptersPageTests {

    val cs = ConfigurationSupport
    val ss = SeleniumSupport()

    fun pageSection(page: Page, title: String): Page = Page(withFragmentIdentifier(page.uri, title), page.name)

    private val linkTextToPageAddresses: Map<String, Page> = mapOf(
        "Introduction" to cs.hostedPages.chapterIntroduction,
        "About this tutorial" to pageSection(cs.hostedPages.chapterIntroduction, "about-this-tutorial"),
        "So what's Haskell?" to pageSection(cs.hostedPages.chapterIntroduction, "so-whats-haskell"),
        "What you need to dive in" to pageSection(cs.hostedPages.chapterIntroduction, "what-you-need"),

        "Starting Out" to cs.hostedPages.chapterStartingOut,
        "Ready, set, go!" to pageSection(cs.hostedPages.chapterStartingOut, "ready-set-go"),
        "Baby's first functions" to pageSection(cs.hostedPages.chapterStartingOut, "babys-first-functions"),
        "An intro to lists" to pageSection(cs.hostedPages.chapterStartingOut, "an-intro-to-lists"),
        "Texas ranges" to pageSection(cs.hostedPages.chapterStartingOut, "texas-ranges"),
        "I'm a list comprehension" to pageSection(cs.hostedPages.chapterStartingOut, "im-a-list-comprehension"),
        "Tuples" to pageSection(cs.hostedPages.chapterStartingOut, "tuples"),

        "Types and Typeclasses" to cs.hostedPages.chapterTypesAndTypeClasses,
        "Believe the type" to pageSection(cs.hostedPages.chapterTypesAndTypeClasses, "believe-the-type"),
        "Type variables" to pageSection(cs.hostedPages.chapterTypesAndTypeClasses, "type-variables"),
        "Typeclasses 101" to pageSection(cs.hostedPages.chapterTypesAndTypeClasses, "typeclasses-101"),

        "Syntax in Functions" to cs.hostedPages.chapterSyntaxInFunctions,
        "Pattern matching" to pageSection(cs.hostedPages.chapterSyntaxInFunctions, "pattern-matching"),
        "Guards, guards!" to pageSection(cs.hostedPages.chapterSyntaxInFunctions, "guards-guards"),
        "Where!?" to pageSection(cs.hostedPages.chapterSyntaxInFunctions, "where"),
        "Let it be" to pageSection(cs.hostedPages.chapterSyntaxInFunctions, "let-it-be"),
        "Case expressions" to pageSection(cs.hostedPages.chapterSyntaxInFunctions, "case-expressions"),

        "Recursion" to cs.hostedPages.chapterRecursion,
        "Hello recursion!" to pageSection(cs.hostedPages.chapterRecursion, "hello-recursion"),
        "Maximum awesome" to pageSection(cs.hostedPages.chapterRecursion, "maximum-awesome"),
        "A few more recursive functions" to pageSection(
            cs.hostedPages.chapterRecursion,
            "a-few-more-recursive-functions"
        ),
        "Quick, sort!" to pageSection(cs.hostedPages.chapterRecursion, "quick-sort"),
        "Thinking recursively" to pageSection(cs.hostedPages.chapterRecursion, "thinking-recursively"),

        "Higher Order Functions" to cs.hostedPages.chapterHigherOrderFunctions,
        "Curried functions" to pageSection(cs.hostedPages.chapterHigherOrderFunctions, "curried-functions"),
        "Some higher-orderism is in order" to pageSection(
            cs.hostedPages.chapterHigherOrderFunctions,
            "higher-orderism"
        ),
        "Maps and filters" to pageSection(cs.hostedPages.chapterHigherOrderFunctions, "maps-and-filters"),
        "Lambdas" to pageSection(cs.hostedPages.chapterHigherOrderFunctions, "lambdas"),
        "Only folds and horses" to pageSection(cs.hostedPages.chapterHigherOrderFunctions, "folds"),
        "Function application with $" to pageSection(
            cs.hostedPages.chapterHigherOrderFunctions,
            "function-application"
        ),
        "Function composition" to pageSection(cs.hostedPages.chapterHigherOrderFunctions, "composition"),

        "Modules" to cs.hostedPages.chapterModules,
        "Loading modules" to pageSection(cs.hostedPages.chapterModules, "loading-modules"),
        "Data.List" to pageSection(cs.hostedPages.chapterModules, "data-list"),
        "Data.Char" to pageSection(cs.hostedPages.chapterModules, "data-char"),
        "Data.Map" to pageSection(cs.hostedPages.chapterModules, "data-map"),
        "Data.Set" to pageSection(cs.hostedPages.chapterModules, "data-set"),
        "Making our own modules" to pageSection(cs.hostedPages.chapterModules, "making-our-own-modules"),

        "Making Our Own Types and Typeclasses" to cs.hostedPages.chapterMakingOurOwnTypeclasses,
        "Algebraic data types intro" to pageSection(
            cs.hostedPages.chapterMakingOurOwnTypeclasses,
            "algebraic-data-types"
        ),
        "Record syntax" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "record-syntax"),
        "Type parameters" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "type-parameters"),
        "Derived instances" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "derived-instances"),
        "Type synonyms" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "type-synonyms"),
        "Recursive data structures" to pageSection(
            cs.hostedPages.chapterMakingOurOwnTypeclasses,
            "recursive-data-structures"
        ),
        "Typeclasses 102" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "typeclasses-102"),
        "A yes-no typeclass" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "a-yes-no-typeclass"),
        "The Functor typeclass" to pageSection(cs.hostedPages.chapterMakingOurOwnTypeclasses, "the-functor-typeclass"),
        "Kinds and some type-foo" to pageSection(
            cs.hostedPages.chapterMakingOurOwnTypeclasses,
            "kinds-and-some-type-foo"
        ),

        "Input and Output" to cs.hostedPages.chapterInputAndOutput,
        "Hello, world!" to pageSection(cs.hostedPages.chapterInputAndOutput, "hello-world"),
        "Files and streams" to pageSection(cs.hostedPages.chapterInputAndOutput, "files-and-streams"),
        "Command line arguments" to pageSection(cs.hostedPages.chapterInputAndOutput, "command-line-arguments"),
        "Randomness" to pageSection(cs.hostedPages.chapterInputAndOutput, "randomness"),
        "Bytestrings" to pageSection(cs.hostedPages.chapterInputAndOutput, "bytestrings"),
        "Exceptions" to pageSection(cs.hostedPages.chapterInputAndOutput, "exceptions"),

        "Functionally Solving Problems" to cs.hostedPages.chapterFunctionallySolvingProblems,
        "Reverse Polish notation calculator" to pageSection(
            cs.hostedPages.chapterFunctionallySolvingProblems,
            "reverse-polish-notation-calculator"
        ),
        "Heathrow to London" to pageSection(cs.hostedPages.chapterFunctionallySolvingProblems, "heathrow-to-london"),

        "Functors, Applicative Functors and Monoids" to cs.hostedPages.chapterFunctorsApplicativeFunctorsAndMonoids,
        "Functors redux" to pageSection(
            cs.hostedPages.chapterFunctorsApplicativeFunctorsAndMonoids,
            "functors-redux"
        ),
        "Applicative functors" to pageSection(
            cs.hostedPages.chapterFunctorsApplicativeFunctorsAndMonoids,
            "applicative-functors"
        ),
        "The newtype keyword" to pageSection(
            cs.hostedPages.chapterFunctorsApplicativeFunctorsAndMonoids,
            "the-newtype-keyword"
        ),
        "Monoids" to pageSection(cs.hostedPages.chapterFunctorsApplicativeFunctorsAndMonoids, "monoids"),

        "A Fistful of Monads" to cs.hostedPages.chapterAFistfulOfMonads,
        "Getting our feet wet with Maybe" to pageSection(
            cs.hostedPages.chapterAFistfulOfMonads,
            "getting-our-feet-wet-with-maybe"
        ),
        "The Monad type class" to pageSection(cs.hostedPages.chapterAFistfulOfMonads, "the-monad-type-class"),
        "Walk the line" to pageSection(cs.hostedPages.chapterAFistfulOfMonads, "walk-the-line"),
        "do notation" to pageSection(cs.hostedPages.chapterAFistfulOfMonads, "do-notation"),
        "The list monad" to pageSection(cs.hostedPages.chapterAFistfulOfMonads, "the-list-monad"),
        "Monad laws" to pageSection(cs.hostedPages.chapterAFistfulOfMonads, "monad-laws"),

        "For a Few Monads More" to cs.hostedPages.chapterForAFewMonadsMore,
        "Writer? I hardly know her!" to pageSection(cs.hostedPages.chapterForAFewMonadsMore, "writer"),
        "Reader? Ugh, not this joke again." to pageSection(cs.hostedPages.chapterForAFewMonadsMore, "reader"),
        "Tasteful stateful computations" to pageSection(cs.hostedPages.chapterForAFewMonadsMore, "state"),
        "Error error on the wall" to pageSection(cs.hostedPages.chapterForAFewMonadsMore, "error"),
        "Some useful monadic functions" to pageSection(
            cs.hostedPages.chapterForAFewMonadsMore,
            "useful-monadic-functions"
        ),
        "Making monads" to pageSection(cs.hostedPages.chapterForAFewMonadsMore, "making-monads"),

        "Zippers" to cs.hostedPages.chapterZippers,
        "Taking a walk" to pageSection(cs.hostedPages.chapterZippers, "taking-a-walk"),
        "A trail of breadcrumbs" to pageSection(cs.hostedPages.chapterZippers, "a-trail-of-breadcrumbs"),
        "Focusing on lists" to pageSection(cs.hostedPages.chapterZippers, "focusing-on-lists"),
        "A very simple file system" to pageSection(cs.hostedPages.chapterZippers, "a-very-simple-file-system"),
        "Watch your step" to pageSection(cs.hostedPages.chapterZippers, "watch-your-step"),
    )

    @Test
    fun `That the title is correct`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.chapters.uriAsText)
            assertEquals("Chapters - $TITLE", title)
        }


    @Test
    fun `That the header contains the correct value`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.chapters.uriAsText)
            assertEquals(TITLE, findElement(byXPath("//div[@id='content']/h1")).text)
        }

    @Test
    fun `That the chapter links contain the correct text and addresses`() =
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.chapters.uriAsText)
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
        ss.drive(
            cs.browserNames.map { ss.browserNameToBrowser[it]!! },
            cs.browserDimensions.map { ss.browserDimensionsToDimension(it) }
        ) { capabilities, browser, dimensions ->
            get(cs.hostedPages.chapters.uriAsText)
            assertEquals(
                "This work is licensed under a Creative Commons Attribution-Noncommercial-Share Alike 3.0 Unported " +
                        "License because I couldn't find a license with an even longer name.",
                findElement(byXPath("//p[last()]")).text
            )
        }
}