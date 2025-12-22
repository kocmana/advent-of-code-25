package helper.math

import kotlin.math.sqrt

data class Position3D(val x: Double, val y: Double, val z: Double) {
    infix fun euriclideanDistance(other: Position3D): Double {
        val dx = other.x - x
        val dy = other.y - y
        val dz = other.z - z
        return sqrt(dx * dx + dy * dy + dz * dz)
    }

    companion object{
        fun fromString(string: String = ","): Position3D {
            val parts = string.split(",").map { it.trim().toDouble() }
            if (parts.size != 3) {
                throw IllegalArgumentException("Invalid position string: $string")
            }
            return Position3D(parts[0], parts[1], parts[2])
        }
    }
}