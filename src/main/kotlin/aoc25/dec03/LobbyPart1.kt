package aoc25.dec03

import Parser

fun main() {
    val input = Parser().readFile("dec03/lobby-input.txt")
    val result = solveLobbyPart1(input)
        .also { println(it) }
}

private fun solveLobbyPart1(input: List<String>) =
    input.asSequence()
        .map { dissolveBatteries(it) }
        .map { identifyMaximumJoltage(it) }
        .reduce { a, b -> a + b }

private fun dissolveBatteries(input: String) =
    input.split("")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toList()

private fun identifyMaximumJoltage(batteries: List<Int>): Int {
    val firstElement = batteries.subList(0, batteries.size - 1).max()
    val secondElement = batteries.subList(batteries.indexOf(firstElement) + 1, batteries.size).max()
    val result = (firstElement * 10) + secondElement
    println("Batteries: $batteries, firstElement: $firstElement, secondElement: $secondElement, result: $result")
    return result
}