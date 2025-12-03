package aoc25.dec03

import Parser

fun main() {
    val input = Parser().readFile("dec03/lobby-input.txt")
    solveLobby(input, 12)
        .also { println("Result: ${it.toBigDecimal().toPlainString()}") }
}
