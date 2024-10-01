package io.github.learnyouahaskell.test

import io.github.learnyouahaskell.test.config.Configuration
import java.net.URI.create as createUri

class HostedPages(configuration: Configuration) {

    val landing: Page = Page(configuration.serving.uri, "landing")
    val faq: Page = landing.resolve("faq.html", "faq")
    val chapters: Page = landing.resolve("chapters.html", "chapters")
    val chapterIntroduction: Page = landing.resolve("introduction.html", "chapter-introduction")
    val chapterStartingOut: Page = landing.resolve("starting-out.html", "chapter-starting-out")
    val chapterTypesAndTypeClasses: Page =
        landing.resolve("types-and-typeclasses.html", "chapter-types-and-typeclasses")
    val chapterSyntaxInFunctions: Page =
        landing.resolve("syntax-in-functions.html", "chapter-syntax-in-functions")
    val chapterRecursion: Page = landing.resolve("recursion.html", "chapter-recursion")
    val chapterHigherOrderFunctions: Page =
        landing.resolve("higher-order-functions.html", "chapter-higher-order-functions")
    val chapterModules: Page = landing.resolve("modules.html", "chapter-modules")
    val chapterMakingOurOwnTypeclasses: Page =
        landing.resolve("making-our-own-types-and-typeclasses.html", "chapter-making-our-own-typeclasses")
    val chapterInputAndOutput: Page = landing.resolve("input-and-output.html", "chapter-input-and-output")
    val chapterFunctionallySolvingProblems: Page =
        landing.resolve("functionally-solving-problems.html", "chapter-functionally-solving-problems")
    val chapterFunctionsApplicativeFunctorsAndMonoids: Page = landing.resolve(
        "functors-applicative-functors-and-monoids.html",
        "chapter-functors-applicative-functors-and-monoids"
    )
    val chapterAFistfulOfMonads: Page = landing.resolve("a-fistful-of-monads.html", "chapter-a-fistful-of-monads")
    val chapterForAFewMonadsMore: Page = landing.resolve("for-a-few-monads-more.html", "chapter-for-a-few-monads-more")
    val chapterZippers: Page = landing.resolve("zippers.html", "chapter-zippers")

    // @todo move - this isn't a hosted page
    val createContentEditRequest =
        createUri("https://github.com/learnyouahaskell/learnyouahaskell.github.io/issues/new/choose")

    fun all(): Iterable<Page> = listOf(
        landing,
        faq,
        chapters,
        chapterIntroduction,
        chapterStartingOut,
        chapterTypesAndTypeClasses,
        chapterSyntaxInFunctions,
        chapterRecursion,
        chapterHigherOrderFunctions,
        chapterModules,
        chapterMakingOurOwnTypeclasses,
        chapterInputAndOutput,
        chapterFunctionallySolvingProblems,
        chapterFunctionsApplicativeFunctorsAndMonoids,
        chapterAFistfulOfMonads,
        chapterForAFewMonadsMore,
        chapterZippers)
}