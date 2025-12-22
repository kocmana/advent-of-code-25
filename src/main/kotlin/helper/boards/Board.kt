package helper.boards

import helper.Parser
import helper.math.Vector

open class Board<T : Any> {
    val board: MutableList<MutableList<T>>

    constructor(file: String, transformingFunction: (String) -> T) {
        val stringArrays = Parser().readFileTo2dArrayAndTranspose(file)
        board = stringArrays.map { row ->
            row.map { transformingFunction(it) }.toMutableList()
        }.toMutableList()
    }

    fun getWidth() = board.size

    fun getHeight() = board[0].size

    fun getPositionsAsSequence(): Sequence<Board<T>.Position> {
        val positions = mutableListOf<Board<T>.Position>()
        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, col ->
                positions.add(Position(i, j))
            }
        }
        return positions.asSequence()
    }

    fun findAllPositionsWhere(predicate: (T) -> Boolean): List<Position> {
        val result = mutableListOf<Position>()
        board.forEachIndexed { x, row ->
            row.forEachIndexed { y, _ ->
                if (predicate(board[x][y])) {
                    result.add(Position(x, y))
                }
            }
        }
        return result
    }

    fun isValidPosition(position: Position): Boolean =
        position.x in board.indices && position.y in board[0].indices

    fun getPositionOrThrow(position: Position): T {
        if (!isValidPosition(position)) {
            throw IllegalArgumentException("Invalid position $position")
        }
        return board[position.x][position.y]
    }

    fun getPosition(position: Position) =
        if (isValidPosition(position)) board[position.x][position.y] else null

    fun setPosition(position: Position, value: T) =
        if (isValidPosition(position)) board[position.x][position.y] = value
        else throw IllegalArgumentException("Invalid position $position")

    open fun print() {
        for (col in board) {
            for (row in col) {
                print(row)
            }
            println()
            println()
        }
    }

    inner class Position(val x: Int, val y: Int) {
        fun getValue() =
            this@Board.getPosition(this)

        fun setValue(value: T) =
            this@Board.setPosition(this, value)

        infix fun andOneStepTo(direction: Direction) =
            Position(this.x + direction.vector.x, this.y + direction.vector.y)

        fun getNextPositionToDirection(direction: Direction) =
            this@Board.getPosition(this andOneStepTo direction)

        fun getApplicableDirections(predicate: (T) -> Boolean) =
            Direction.entries.filter {
                getNextPositionToDirection(it)
                    ?.let { value -> predicate(value) }
                    ?: false
            }

        fun getNeighbouringPositions() =
            Direction.entries.map { this.andOneStepTo(it) }
                .filter { this@Board.isValidPosition(it) }
                .toList()

        fun getNeighboringPositionsWhere(predicate: (T) -> Boolean) =
            this.getNeighbouringPositions()
                .filter { predicate (it.getValue()!!) }
                .toList()

        override fun toString(): String =
            "{X: $x, Y: $y, Value: ${getValue()}}"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Board<*>.Position

            if (x != other.x) return false
            if (y != other.y) return false

            return true
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }

    }
}

enum class Direction(val vector: Vector) {
    TOP_LEFT(Vector(-1, -1)),
    UP(Vector(0, -1)),
    TOP_RIGHT(Vector(1, -1)),
    RIGHT(Vector(1, 0)),
    BOTTOM_RIGHT(Vector(1, 1)),
    DOWN(Vector(0, 1)),
    BOTTOM_LEFT(Vector(-1, 1)),
    LEFT(Vector(-1, 0));
}