package helper

import helper.math.Vector

data class Position(val x: Long, val y: Long) {
    operator fun plus(vector: Vector) = Position(x + vector.x, y + vector.y)
    operator fun minus(vector: Vector) = Position(x - vector.x, y - vector.y)

    fun areaToInclusive(other: Position) =
        (kotlin.math.abs(other.x - x) + 1) * (kotlin.math.abs(other.y - y) + 1)
}