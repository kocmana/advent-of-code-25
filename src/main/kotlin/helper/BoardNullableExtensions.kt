package at.kocmana.helper

import helper.Board
import helper.Direction

fun <T : Any> Board<T>.Position.getNeighboringPositionsAndNullWhere(predicate: (T?) -> Boolean) =
    Direction.entries.map { this andOneStepTo (it) }
        .filter { predicate(it.getValue()) }
        .toList()