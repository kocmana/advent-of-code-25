package aoc25.dec02

import helper.Parser

val patternPart1 = Regex("""^(\d+)\1{1}$""")

fun main() {
    val input = Parser().readFileAsString("/dec02/gift-shop-input.txt")
    val result = dissolveRanges(input).asSequence()
        .map { identifyInvalidIds(it, patternPart1) }
        .reduce { a, b -> a + b }.also { println(it) }
        .reduce { a, b -> a + b }
    println(result)
}

fun solvePuzzle(input: String, pattern: Regex) =
    dissolveRanges(input).asSequence()
        .map { identifyInvalidIds(it, pattern) }
        .reduce { a, b -> a + b }
        .reduce { a, b -> a + b }

fun dissolveRanges(input: String) =
    input.split(",")
        .map { rangeStr -> rangeStr.split("-").map { it.toLong() } }
        .map { it[0]..it[1] }

fun identifyInvalidIds(idRanges: LongRange, pattern: Regex): List<Long> =
    idRanges.asSequence()
        .map { it.toString() }
        .filter { pattern.matches(it) }
        .map(String::toLong)
        .toList()