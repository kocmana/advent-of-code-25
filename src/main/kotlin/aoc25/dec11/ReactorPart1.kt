package aoc25.dec11

import helper.Parser

val nodes = mutableMapOf<String, CableNode>()

fun main() {
    val input = Parser().readFile("/dec11/reactor-input.txt")
        .map { parseCableNodeInput(it) }

    buildNodes(input)
    val startNode = nodes["you"]
    val endNode = nodes["out"]

    println(traverse(startNode!!, endNode!!, listOf()).size)
}

fun parseCableNodeInput(input: String): Pair<String, List<String>> {
    val (identity, outputs) = input.split(": ", limit = 2)
    val outputList = outputs.split(" ").asSequence()
        .map(String::trim)
        .toList()
    return identity to outputList
}

fun buildNodes(input: List<Pair<String, List<String>>>) {
    input.map { CableNode(it.first) }
        .forEach { nodes[it.identity] = it }

    nodes["out"] = CableNode("out")
    input.forEach { (identity, outputs) ->
        nodes[identity].let { node ->
            outputs.forEach { outputId ->
                nodes[outputId]?.let { outputNode ->
                    node?.addOutput(outputNode)
                }
            }
        }
    }
}

fun traverse(current: CableNode, end: CableNode, history: List<CableNode>): List<CableNode> {
    if (current == end) {
        return listOf(current)
    }

     return current.output.asSequence()
         .flatMap{ traverse(it, end, history) }
         .toList()
}