package aoc25.dec06

import helper.Parser
import helper.splitListIntoSublistsBasedOnDelimiter
import helper.transposeArray
import kotlin.sequences.map

fun main() {
    val input = Parser().readFileTo2dArray("/dec06/trash-compactor-input.txt")
    val instructions = parseInstructions(input)
    val numbers = parseNumbers(input)

    calculateResult(instructions, numbers)
        .also { println(it) }
}

private fun parseInstructions(input: Array<Array<String>>) =
    input[input.size - 1].asSequence()
        .filter { it.isNotBlank() }
        .map(Operator::fromSymbol)
        .toList()

private fun parseNumbers(input: Array<Array<String>>) =
    transposeArray(input.dropLast(1).toTypedArray(), times = 1)
        .map { lineArray -> lineArray.joinToString("") }
        .let { splitListIntoSublistsBasedOnDelimiter(it, Regex("^\\s+$")) }
        .map { list -> list.map { it.trim().toLong() } }
        .map { it.reversed() }

private fun calculateResult(instructions: List<Operator>, numbers: List<List<Long>>) =
    instructions.mapIndexed { index, operator -> Calculation(numbers[index], operator) }
        .sumOf { it.solve() }