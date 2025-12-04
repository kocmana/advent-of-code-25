package aoc25.dec04

import helper.getNeighboringPositionsAndNullWhere
import helper.Board

fun main() {
    val board = Board<String>(file = "dec04/printing-departement-input.txt", transformingFunction = { it })
    board.getPositionsAsSequence()
        .filter{ it.getValue() == "@" }
        .filter { it.getNeighboringPositionsAndNullWhere { value -> value == "@" }.size < 4 }
        .count()
        .also { println("Result: $it") }
}