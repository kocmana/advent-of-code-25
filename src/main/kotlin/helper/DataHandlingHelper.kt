package at.kocmana.helper

fun <T> getPairCombinations(input: List<T>): List<Pair<T, T>> {
    val combinations = mutableListOf<Pair<T, T>>()
    input.forEach { element ->
        val otherElements = input.minus(element)
        otherElements.forEach { combinations.add(element to it) }
    }
    return combinations.toList()
}

fun <T> MutableList<T>.cutAndInsertPartialList(fromIndex: Int, length: Int, targetIndex: Int) {
    val sublist = this.subList(fromIndex, fromIndex + length).toList()
    this.subList(fromIndex, fromIndex + length).apply {
        for (i in indices) this[i] = null as T
    }
    this.subList(targetIndex, targetIndex + length).clear()

    val adjustedTargetIndex =
        if (targetIndex > fromIndex) targetIndex - length
        else targetIndex

    this.addAll(adjustedTargetIndex, sublist)
}

fun parseBoardAsInt(board: Array<Array<String>>) =
    board.map { row ->
        row.map { it.toInt() }.toTypedArray()
    }.toTypedArray()

