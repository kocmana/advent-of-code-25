package aoc25.dec01

class EntranceDial(var currentPosition: Int = 50, var counter: Int = 0) {

    fun updatePosition(input: DialInstruction): Unit {
        currentPosition = when (input.direction) {
            Direction.CLOCKWISE -> (currentPosition + input.steps) % 100
            Direction.COUNTER_CLOCKWISE -> ((currentPosition - input.steps) % 100 + 100) % 100
        }
    }

    data class DialInstruction(val direction: Direction, val steps: Int)

    enum class Direction {
        CLOCKWISE, COUNTER_CLOCKWISE
    }
}
