package at.kocmana.helper


class Board<T : Any> {
    val board: List<List<T>>
    val agents: MutableList<Agent> = mutableListOf()

    constructor(file: String, transformingFunction: (String) -> T) {
        val stringArrays = Parser().readFileTo2dArrayAndTranspose(file)
        board = stringArrays.map { row ->
            row.map { transformingFunction(it) }.toList()
        }.toList()
    }

    fun generateAgent(startingPosition: Position): Agent {
        if (!isValidPosition(startingPosition)) {
            throw IllegalArgumentException("Invalid start position")
        }
        val agent = Agent(startingPosition)
        agents.add(agent)
        return agent
    }

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

    inner class Agent internal constructor(var position: Position) {

        infix fun canMoveTo(direction: Direction) =
            this@Board.isValidPosition(position andOneStepTo direction)

        infix fun move(direction: Direction) {
            val newPosition = position andOneStepTo direction
            if (canMoveTo(direction)) this.position = this.position andOneStepTo direction
        }

        fun getApplicableDirections(predicate: (T) -> Boolean) =
            Direction.entries.filter {
                this.position.getNextPositionToDirection(it)
                    ?.let { value -> predicate.invoke(value) }
                    ?: false
            }

        fun moveWhereApplicable(predicate: (T) -> Boolean) {
            val direction = Direction.entries.first {
                this.position.getNextPositionToDirection(it)
                    ?.let { value -> predicate.invoke(value) }
                    ?: false
            }
            position = position andOneStepTo direction
        }
    }

    inner class Position(val x: Int, val y: Int) {
        fun getValue() =
            if(this@Board.isValidPosition(this)) this@Board.getPosition(this) else null

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
    UP(Vector(0, -1)),
    DOWN(Vector(0, 1)),
    LEFT(Vector(-1, 0)),
    RIGHT(Vector(1, 0));
}