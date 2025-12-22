package aoc25.dec09

import helper.Parser
import helper.Position
import helper.getPairCombinationsBidirectional


fun main() {
    Parser().readFile("dec09/movie-theater-input.txt").asSequence()
        .map { it.split(",") }
        .map { Position(it[0].toLong(), it[1].toLong()) }
        .toList()
        .let { getPairCombinationsBidirectional(it) }
        .let { getMaxDistance(it) }
        .also { println(it) }
}

fun getMaxDistance(positions: Set<Pair<Position, Position>>) =
    positions.maxOfOrNull { it.first.areaToInclusive(it.second) }

