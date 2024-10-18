package io.github.learnyouahaskell.test.site

data class Chapter(val page: Page, val navigationLinkToText: String? = null)

class Chapters(hostedPages: HostedPages) {

    companion object {
        const val PREVIOUS_LINK_CLASS: String = "prevlink"
        const val NEXT_LINK_CLASS: String = "nxtlink"
    }

    val navigation: List<Chapter> = listOf(
        Chapter(hostedPages.chapterIntroduction, "Introduction"),
        Chapter(hostedPages.chapterStartingOut, "Starting Out"),
        Chapter(hostedPages.chapterTypesAndTypeClasses, "Types and Typeclasses"),
        Chapter(hostedPages.chapterSyntaxInFunctions, "Syntax in Functions"),
        Chapter(hostedPages.chapterRecursion, "Recursion"),
        Chapter(hostedPages.chapterHigherOrderFunctions, "Higher Order Functions"),
        Chapter(hostedPages.chapterModules, "Modules"),
        Chapter(hostedPages.chapterMakingOurOwnTypeclasses, "Making Our Own Types and Typeclasses"),
        Chapter(hostedPages.chapterInputAndOutput, "Input and Output"),
        Chapter(hostedPages.chapterFunctionallySolvingProblems, "Functionally Solving Problems"),
        Chapter(hostedPages.chapterFunctorsApplicativeFunctorsAndMonoids, "Functors, Applicative Functors and Monoids"),
        Chapter(hostedPages.chapterAFistfulOfMonads, "A Fistful of Monads"),
        Chapter(hostedPages.chapterForAFewMonadsMore, "For a Few Monads More"),
        Chapter(hostedPages.chapterZippers, "Zippers")
    )
}