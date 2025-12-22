package aoc25.dec05

import helper.Parser

fun main() {
    val input = Parser().readFile("/dec05/cafeteria-input.txt")
    val (ranges, ids) = extractInstructions(input)
    checkIfWithinRanges(ids, ranges)
        .also { println("Result: $it") }
}

fun checkIfWithinRanges(ids: List<Long>, ranges: List<LongRange>) =
    ids.asSequence()
        .filter { ranges.any { range -> it in range } }
        .count()

fun extractInstructions(input: List<String>): Pair<List<LongRange>, List<Long>> {
    val separator = input.indexOfFirst { it == "" }
    val ranges = extractRanges(input.subList(0, separator))
    val ids = input.subList(separator + 1, input.size)
        .map { it.toLong() }
    return ranges to ids
}

fun extractRanges(ranges: List<String>) =
    ranges.map {
        val (from, to) = it.split("-")
        LongRange(from.toLong(), to.toLong())
    }