package helper.boards

class AgentBoard<T : Any>(file: String, transformingFunction: (String) -> T) : Board<T>(file, transformingFunction) {

    val agents: MutableSet<Agent> = mutableSetOf()

    fun generateAgent(startingPosition: Position): Agent {
        require (isValidPosition(startingPosition)) {
            throw IllegalArgumentException("Invalid start position")
        }
        val agent = Agent(startingPosition)
        agents.add(agent)
        return agent
    }

    fun cleanAgents() {
        agents.removeAll { it.markedForRemoval }
    }

    override fun print() {
        Array(board.size) { x ->
            Array(board[0].size) { y ->
                if (agents.any { it.position.x == x && it.position.y == y }) {
                    "-"
                } else {
                    board[x][y].toString()
                }
            }
        }.forEach { row ->
            row.forEach { print(it) }
            println()
        }
        println("----------------------------------------- ")
    }

    inner class Agent internal constructor(var position: Board<T>.Position, var markedForRemoval: Boolean = false) {

        infix fun canMoveTo(direction: Direction) =
            this@AgentBoard.isValidPosition(position andOneStepTo direction)

        infix fun move(direction: Direction) {
            if (canMoveTo(direction)) this.position = this.position andOneStepTo direction
        }

        fun markForRemoval() {
            this.markedForRemoval = true
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

        fun getAsciiRepresentation() =
            "@"

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as AgentBoard<*>.Agent

            return position == other.position
        }

        override fun hashCode(): Int {
            return position.hashCode()
        }
    }

}