package aoc25.dec04

import helper.boards.Board
import helper.boards.getNeighboringPositionsAndNullWhere

fun main() {
    val board = Board<String>(file = "/dec04/printing-departement-input.txt", transformingFunction = { it })
    solvePrintingDepartement(board)
        .also { println("Result: $it") }
}

fun solvePrintingDepartement(board: Board<String>): Int {
    var counter = 0
    var changes = 0
    do {
        val positionsToRemove = identifyPositions(board)
        positionsToRemove.forEach { it.setValue(".") }
        changes = positionsToRemove.size
        counter += changes
        println("Changes: $changes, Total removed: $counter")
    } while (changes > 0)
    return counter
}

fun identifyPositions(board: Board<String>) =
    board.getPositionsAsSequence()
        .filter { it.getValue() == "@" }
        .filter { it.getNeighboringPositionsAndNullWhere { value -> value == "@" }.size < 4 }
        .toList()
