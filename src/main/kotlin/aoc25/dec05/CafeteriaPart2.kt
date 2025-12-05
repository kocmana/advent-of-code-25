package aoc25.dec05

import Parser

fun main() {
    val input = Parser().readFile("dec05/cafeteria-input.txt")
    val (ranges, _) = extractInstructions(input)
    overlapRanges(ranges)
        .sumOf { it.last - it.first  + 1}
        .also { println("Result: $it") }
}

fun overlapRanges(ranges: List<LongRange>): Set<LongRange> {
    val modifiedRanges = ranges.toMutableSet()
    do {
        val beforeSize = modifiedRanges.size
        loop@for (range in modifiedRanges) {
            val otherElements = modifiedRanges.filter { it != range }
            otherElements
                .sortedBy { it.first }
                .forEach { inner ->
                    when {
                        inner.first > range.first && inner.last < range.last -> {
                            modifiedRanges.remove(inner)
                            break@loop
                        }
                        inner.first <= range.first && inner.last in range -> {
                            modifiedRanges.remove(inner)
                            modifiedRanges.remove(range)
                            modifiedRanges.add(LongRange(inner.first, range.last))
                            break@loop
                        }
                        inner.last >= range.last && inner.first in range -> {
                            modifiedRanges.remove(inner)
                            modifiedRanges.remove(range)
                            modifiedRanges.add(LongRange(range.first, inner.last))
                            break@loop
                        }
                    }
                }
        }
    } while (beforeSize != modifiedRanges.size)
    return modifiedRanges
}