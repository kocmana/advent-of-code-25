package at.kocmana.helper

fun <T : Any> Board<T>.Position.getNeighboringPositionsAndNullWhere(predicate: (T?) -> Boolean) =
    Direction.entries.map { this andOneStepTo (it) }
        .filter { predicate(it.getValue()) }
        .toList()