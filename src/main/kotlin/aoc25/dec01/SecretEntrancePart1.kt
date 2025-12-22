package aoc25.dec01

import helper.Parser


fun main() {
    val input = Parser().readFile("/dec01/secret-entrance-input.txt")
        .map{ parseLine(it) }
    val entranceDial = EntranceDial()
    entranceDial.solvePart1(input)
        .also { println(entranceDial.counter) }
}

fun parseLine(line: String): EntranceDial.DialInstruction {
    val direction = when (line[0]) {
        'R' -> EntranceDial.Direction.CLOCKWISE
        'L' -> EntranceDial.Direction.COUNTER_CLOCKWISE
        else -> throw IllegalArgumentException("Unexpected direction: ${line[0]}")
    }
    val steps = line.substring(1).toInt()
    return EntranceDial.DialInstruction(direction, steps)
}

fun EntranceDial.solvePart1(input: List<EntranceDial.DialInstruction>) {
    for (instruction in input) {
        updatePosition(instruction)
        if(this.currentPosition == 0 ) { ++this.counter}
        println("At position $currentPosition, applying instruction $instruction, number of zeroes: $counter")
    }
}