package aoc25.dec03

import Parser
import kotlin.math.pow

fun main() {
    val input = Parser().readFile("dec03/lobby-input.txt")
    solveLobby(input, 2)
        .also { println("Result: ${it.toBigDecimal().toPlainString()}") }
}

fun solveLobby(input: List<String>, numberOfBatteries: Int) =
    input.asSequence()
        .map { toIntList(it) }
        .map { identifyMaximumJoltage(it, numberOfBatteries)
            .also { println(it.toBigDecimal().toPlainString()) } }
        .reduce { a, b -> a + b }

private fun toIntList(input: String) =
    input.split("")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toList()

private fun identifyMaximumJoltage(batteries: List<Int>, numberOfRemainingBatteries: Int): Double {
    if (numberOfRemainingBatteries == 0) {
        return 0.0
    }

    val element = batteries.subList(0, batteries.size - numberOfRemainingBatteries + 1).max()
    val remainingBatteries = batteries.subList(batteries.indexOf(element) + 1, batteries.size)
    val otherElements = identifyMaximumJoltage(remainingBatteries, numberOfRemainingBatteries - 1)
    val result = (element * 10.0.pow(numberOfRemainingBatteries.toDouble() - 1)) + otherElements
    return result
}