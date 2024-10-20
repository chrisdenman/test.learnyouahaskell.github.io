package io.github.learnyouahaskell.test.site

import io.github.learnyouahaskell.test.util.Image

class HostedImages {

    companion object {
        private fun createImage(relativeResourceUrl: String, alt: String) =
            Image.Companion.create("assets/images/${relativeResourceUrl}", alt)
    }

    val turtle = createImage("faq/turtle.png", "turtle???")
    val bird = createImage("introduction/bird.png", "bird")
    val fx = createImage("introduction/fx.png", "fx")
    val lazy = createImage("introduction/lazy.png", "lazy")
    val boat = createImage("introduction/boat.png", "boat")
    val startingOut = createImage("starting-out/startingout.png", "egg")
    val ringRing = createImage("starting-out/ringring.png", "phoen")
    val baby = createImage("starting-out/baby.png", "this is you")
    val list = createImage("starting-out/list.png", "BUY A DOG")
    val listMonster = createImage("starting-out/listmonster.png", "list monster")
    val cowboy = createImage("starting-out/cowboy.png", "draw")
    val kermit = createImage("starting-out/kermit.png", "frog")
    val setNotation = createImage("starting-out/setnotation.png", "set notation")
    val tuple = createImage("starting-out/tuple.png", "tuples")
    val pythagoras = createImage("starting-out/pythag.png", "look at meee")
    val cow = createImage("types-and-typeclasses/cow.png", "moo")
    val bomb = createImage("types-and-typeclasses/bomb.png", "bomb")
    val box = createImage("types-and-typeclasses/box.png", "box")
    val classes = createImage("types-and-typeclasses/classes.png", "class")
    val pattern = createImage("syntax-in-functions/pattern.png", "four!")
    val guards = createImage("syntax-in-functions/guards.png", "guards")
    val letItBe = createImage("syntax-in-functions/letitbe.png", "let it be")
    val case = createImage("syntax-in-functions/case.png", "case")
    val recursion = createImage("recursion/recursion.png", "SOVIET RUSSIA")
    val maxS = createImage("recursion/maxs.png", "max")
    val painter = createImage("recursion/painter.png", "painter")
    val quickMan = createImage("recursion/quickman.png", "quickman")
    val quickSort = createImage("recursion/quicksort.png", "quicksort")
    val brain = createImage("recursion/brain.png", "brain")
    val sun = createImage("higher-order-functions/sun.png", "sun")
    val curry = createImage("higher-order-functions/curry.png", "haskell curry")
    val bonus = createImage("higher-order-functions/bonus.png", "rocktopus")
    val map = createImage("higher-order-functions/map.png", "map")
    val lambda = createImage("higher-order-functions/lambda.png", "lambda")
    val lamb = createImage("higher-order-functions/lamb.png", "lamb")
    val origami = createImage("higher-order-functions/origami.png", "folded bird")
    val foldl = createImage("higher-order-functions/foldl.png", "foldl")
    val washingMachine = createImage("higher-order-functions/washmachine.png", "fold this up!")
    val dollar = createImage("higher-order-functions/dollar.png", "dollar")
    val composition = createImage("higher-order-functions/composition.png", " (f . g)(x) = f(g(x))")
    val notes = createImage("higher-order-functions/notes.png", "notes")
    val modules = createImage("modules/modules.png", "modules")
    val legoLists = createImage("modules/legolists.png", "shopping lists")
    val legoChar = createImage("modules/legochar.png", "lego char")
    val legoMap = createImage("modules/legomap.png", "legomap")
    val legoSets = createImage("modules/legosets.png", "legosets")
    val makingModules = createImage("modules/making_modules.png", "making modules")
    val caveman = createImage("making-our-own-types-and-typeclasses/caveman.png", "caveman")
    val record = createImage("making-our-own-types-and-typeclasses/record.png", "record")
    val yeti = createImage("making-our-own-types-and-typeclasses/yeti.png", "yeti")
    val meerkat = createImage("making-our-own-types-and-typeclasses/meekrat.png", "meekrat")
    val gob = createImage("making-our-own-types-and-typeclasses/gob.png", "gob")
    val chicken = createImage("making-our-own-types-and-typeclasses/chicken.png", "chicken")
    val theFonz = createImage("making-our-own-types-and-typeclasses/thefonz.png", "the fonz")
    val binaryTree = createImage("making-our-own-types-and-typeclasses/binarytree.png", "binary search tree")
    val trafficLight = createImage("making-our-own-types-and-typeclasses/trafficlight.png", "tweet")
    val yesNo = createImage("making-our-own-types-and-typeclasses/yesno.png", "yesno")
    val functor = createImage("making-our-own-types-and-typeclasses/functor.png", "I AM FUNCTOOOOR!!!")
    val typeFoo = createImage("making-our-own-types-and-typeclasses/typefoo.png", "TYPE FOO MASTER")
    val dogNap = createImage("input-and-output/dognap.png", "poor dog")
    val helloWorld = createImage("input-and-output/helloworld.png", "HELLO!")
    val luggage = createImage("input-and-output/luggage.png", "luggage")
    val streams = createImage("input-and-output/streams.png", "streams")
    val file = createImage("input-and-output/file.png", "A FILE IN A CAKE!!!")
    val edd = createImage("input-and-output/edd.png", "butter toast")
    val arguments = createImage("input-and-output/arguments.png", "COMMAND LINE ARGUMENTS!!! ARGH")
    val salad = createImage("input-and-output/salad.png", "fresh baked salad")
    val random = createImage("input-and-output/random.png", "this picture is the ultimate source of randomness and wackiness")
    val jackOfDiamonds = createImage("input-and-output/jackofdiamonds.png", "jack of diamonds")
    val chainChomp = createImage("input-and-output/chainchomp.png", "like normal string, only they byte ... what a pedestrian pun this is")
    val timber = createImage("input-and-output/timber.png", "timberr!!!!")
    val police = createImage("input-and-output/police.png", "Stop right there, criminal scum! Nobody breaks the law on my watch! Now pay your fine or it's off to jail.")
    val puppy = createImage("input-and-output/puppy.png", "non sequitur")
    val calculator = createImage("functionally-solving-problems/calculator.png", "HA HA HA")
    val roads = createImage("functionally-solving-problems/roads.png", "Heathrow - London")
    val roadsSimple = createImage("functionally-solving-problems/roads_simple.png", "roads")
    val guyCar = createImage("functionally-solving-problems/guycar.png", "this is you")
    val frogTor = createImage("functors-applicative-functors-and-monoids/frogtor.png", "frogs dont even need money")
    val alien = createImage("functors-applicative-functors-and-monoids/alien.png", "w00ooOoooOO")
    val lifter = createImage("functors-applicative-functors-and-monoids/lifter.png", "lifting a function is easier than lifting a million pounds")
    val justice = createImage("functors-applicative-functors-and-monoids/justice.png", "justice is blind, but so is my dog")
    val present = createImage("functors-applicative-functors-and-monoids/present.png", "disregard this analogy")
    val whale = createImage("functors-applicative-functors-and-monoids/whale.png", "whale")
    val knight = createImage("functors-applicative-functors-and-monoids/knight.png", "ahahahah!")
    val jazzb = createImage("functors-applicative-functors-and-monoids/jazzb.png", "SLAP")
    val maoi = createImage("functors-applicative-functors-and-monoids/maoi.png", "why so serious?")
    val krakatoa = createImage("functors-applicative-functors-and-monoids/krakatoa.png", "wow, very evil")
    val shamrock = createImage("functors-applicative-functors-and-monoids/shamrock.png", "top of the morning to ya!!!")
    val pirateShip = createImage("functors-applicative-functors-and-monoids/pirateship.png", "wow this is pretty much the gayest pirate ship ever")
    val balloonDog = createImage("functors-applicative-functors-and-monoids/balloondog.png", "woof dee do!!!")
    val smug = createImage("functors-applicative-functors-and-monoids/smug.png", "smug as hell")
    val bear = createImage("functors-applicative-functors-and-monoids/bear.png", "did anyone ORDER pizza?!?! I can't BEAR these puns!")
    val accordion = createImage("functors-applicative-functors-and-monoids/accordion.png", "find the visual pun or whatever")
    val smugPig = createImage("a-fistful-of-monads/smugpig.png", "more cool than u")
    val buddha = createImage("a-fistful-of-monads/buddha.png", "monads, grasshoppa")
    val kid = createImage("a-fistful-of-monads/kid.png", "this is you on monads")
    val turtleTwo = createImage("a-fistful-of-monads/tur2.png", "hmmm yaes")
    val pierre = createImage("a-fistful-of-monads/pierre.png", "pierre")
    val banana = createImage("a-fistful-of-monads/banana.png", "iama banana")
    val centaur = createImage("a-fistful-of-monads/centaur.png", "john joe glanton")
    val owld = createImage("a-fistful-of-monads/owld.png", "90s owl")
    val deadCat = createImage("a-fistful-of-monads/deadcat.png", "dead cat")
    val concatMap = createImage("a-fistful-of-monads/concatmap.png", "concatmap")
    val chess = createImage("a-fistful-of-monads/chess.png", "hee haw im a horse")
    val judgeDog = createImage("a-fistful-of-monads/judgedog.png", "the court finds you guilty of peeing all over everything")
    val clintEastwood = createImage("for-a-few-monads-more/clint.png", "there are two kinds of people in the world, my friend. those who learn them a haskell and those who have the job of coding java")
    val tuco = createImage("for-a-few-monads-more/tuco.png", "when you have to poop, poop, don't talk")
    val angelEyes = createImage("for-a-few-monads-more/angeleyes.png", "when you have to poop, poop, don't talk")
    val cactus = createImage("for-a-few-monads-more/cactus.png", "cactuses")
    val revolver = createImage("for-a-few-monads-more/revolver.png", "bang you're dead")
    val texas = createImage("for-a-few-monads-more/texas.png", "don't jest with texas")
    val badge = createImage("for-a-few-monads-more/badge.png", "im a cop")
    val wolf = createImage("for-a-few-monads-more/wolf.png", "im a cop too")
    val tipi = createImage("for-a-few-monads-more/tipi.png", "im a cop too as well also")
    val miner = createImage("for-a-few-monads-more/miner.png", "i've found yellow!")
    val spearHead = createImage("for-a-few-monads-more/spearhead.png", "kewl")
    val prob = createImage("for-a-few-monads-more/prob.png", "probs")
    val ride = createImage("for-a-few-monads-more/ride.png", "ride em cowboy")
    val sixtiesDude = createImage("zippers/60sdude.png", "hi im chet")
    val pollyWantsA = createImage("zippers/pollywantsa.png", "polly says her back hurts")
    val bread = createImage("zippers/bread.png", "whoop dee doo")
    val almostZipper = createImage("zippers/almostzipper.png", "almostthere")
    val asstronaut = createImage("zippers/asstronaut.png", "asstronaut")
    val picard = createImage("zippers/picard.png", "the best damn thing")
    val spongeDisk = createImage("zippers/spongedisk.png", "spongedisk")
    val cool = createImage("zippers/cool.png", "wow cool great")
    val bigTree = createImage("zippers/bigtree.png", "falling for you")

    val all: List<Image> = listOf(
        turtle, bird, fx, lazy, boat, startingOut, ringRing, baby, list, listMonster, cowboy, kermit, setNotation,
        tuple, pythagoras, cow, bomb, box, classes, pattern, guards, letItBe, case, recursion, maxS, painter, quickMan,
        quickSort, brain, sun, curry, bonus, map, lambda, lamb, origami, foldl, washingMachine, dollar, composition,
        notes, modules, legoLists, legoChar, legoMap, legoSets, makingModules, caveman, record, yeti, meerkat, gob,
        chicken, theFonz, binaryTree, trafficLight, yesNo, functor, typeFoo, dogNap, helloWorld, luggage, streams,
        file, edd, arguments, salad, random, jackOfDiamonds, chainChomp, timber, police, puppy, calculator, roads,
        roadsSimple, guyCar, frogTor, alien, lifter, justice, present, whale, knight, jazzb, maoi, krakatoa, shamrock,
        pirateShip, balloonDog, smug, bear, accordion, smugPig, buddha, kid, turtleTwo, pierre, banana, centaur, owld,
        deadCat, concatMap, chess, judgeDog, clintEastwood, tuco, angelEyes, cactus, revolver, texas, badge, wolf, tipi,
        miner, spearHead, prob, ride, sixtiesDude, pollyWantsA, bread, almostZipper, asstronaut, picard, spongeDisk,
        cool, bigTree
    )
}