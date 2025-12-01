package at.kocmana.helper

data class Position (val x: Int, val y: Int) {
    operator fun plus(vector: Vector) = Position(x + vector.x, y + vector.y)
    operator fun minus(vector: Vector) = Position(x - vector.x, y - vector.y)
}