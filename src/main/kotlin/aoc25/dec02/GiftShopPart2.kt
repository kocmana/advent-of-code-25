package aoc25.dec02

import helper.Parser

val patternPart2 = Regex("""^(\d+)\1{1,}$""")

fun main() {
    val input = Parser().readFileAsString("/dec02/gift-shop-input.txt")
    val result = solvePuzzle(input, patternPart2)
    println(result)
}
