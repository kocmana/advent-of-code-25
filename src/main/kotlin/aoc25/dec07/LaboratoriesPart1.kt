package aoc25.dec07

import helper.boards.AgentBoard
import helper.boards.Board
import helper.boards.Direction

fun main() {
    val board = AgentBoard("dec07/laboratories-input.txt", LaboratoriesElements::fromSymbol)
    val start = board.findAllPositionsWhere { it == LaboratoriesElements.START }.first()
    board.generateAgent(start)
    val newAgents = mutableSetOf<AgentBoard<LaboratoriesElements>.Agent>()
    val positionsWithSplit = mutableSetOf<Board<LaboratoriesElements>.Position>()
    for (steps in 1..board.getWidth()) {
        board.agents.forEach {
            it.move(Direction.DOWN)
            if (it.position.getValue() == LaboratoriesElements.SPLITTER) {
                val leftAgent = board.Agent(it.position andOneStepTo Direction.LEFT)
                val rightAgent = board.Agent(it.position andOneStepTo Direction.RIGHT)
                newAgents.addAll(setOf(leftAgent, rightAgent))
                it.markedForRemoval = true
                positionsWithSplit.add(it.position)
            }
        }
        board.cleanAgents()
        board.agents.addAll(newAgents)
        newAgents.clear()
    }
    println("Total split events: ${positionsWithSplit.size} and resulting beams: ${board.agents.size}")
}

enum class LaboratoriesElements(val symbol: String) {
    START("S"),
    SPLITTER("^"),
    EMPTY(".");

    override fun toString(): String = symbol

    companion object {
        fun fromSymbol(symbol: String) =
            entries.first { it.symbol == symbol }
    }
}