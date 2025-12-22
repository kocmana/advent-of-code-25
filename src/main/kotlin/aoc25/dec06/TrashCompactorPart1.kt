package aoc25.dec06

import helper.Parser

fun main() {
    val input = Parser().readFile("dec06/trash-compactor-input.txt");
    val parsedInput = parseInput(input)
    solve(parsedInput)
        .also { println("Result: $it") }
}

fun solve(parsedInput: List<Calculation>) =
    parsedInput.sumOf { it.solve() }

fun parseInput(input: List<String>): List<Calculation> {
    val operands = input.subList(0, input.size - 1)
    val operations = input[input.size - 1]
        .split(" ").asSequence()
        .map { it.trim() }
        .filter { it.isNotBlank() }
        .toList()

    val operandsAsNumbers = operands.asSequence()
        .map {
            it.split(" ")
                .map { string -> string.trim() }
                .filter { it.isNotBlank() }
                .map { string -> string.toLong() }
        }
        .toList()

    return operandsAsNumbers[0].asSequence().withIndex()
        .map { (index, element) ->
            operandsAsNumbers.asSequence()
                .map { list -> list[index] }
                .toList()
        }.withIndex()
        .map { (index, element) ->
            when (operations[index]) {
                "+" -> Calculation(element, Operator.PLUS)
                "*" -> Calculation(element, Operator.MULTIPLY)
                else -> throw IllegalStateException("Unreachable state")
            }
        }
        .toList()
}

data class Calculation(val operands: List<Long>, val operator: Operator) {
    fun solve() = when (operator) {
        Operator.PLUS -> operands.reduce { acc, v -> acc + v }
        Operator.MULTIPLY -> operands.reduce { acc, v -> acc * v }
    }
}

enum class Operator(val value: String) {
    PLUS("+"),
    MULTIPLY("*");

    companion object {
        fun fromSymbol(symbol: String) =
            entries.first { it.value == symbol }
    }
}