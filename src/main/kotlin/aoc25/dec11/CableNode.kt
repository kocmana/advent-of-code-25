package aoc25.dec11

data class CableNode(val identity: String, val output: MutableList<CableNode>) {

    constructor(identity: String) : this(identity, mutableListOf())

    fun addOutput(node: CableNode): CableNode {
        this.output += node
        return this
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CableNode

        return identity == other.identity
    }

    override fun hashCode(): Int {
        return identity.hashCode()
    }
}