package helper

class Parser {

    fun readFile(filename: String): List<String> =
        this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readLines()
            ?: throw IllegalArgumentException("File $filename not found")

    fun readFileAsString(filename: String): String =
        this::class.java.getResourceAsStream(filename)?.bufferedReader()?.readText()
            ?: throw IllegalArgumentException("File $filename not found")

    fun <T> readAndModify(filename: String, modifier: (String) -> T): List<T> =
        readFile(filename).map(modifier)

    fun readToIntLists(filename: String, splitter: String): List<List<Int>> =
        readFile(filename)
            .asSequence()
            .map {
                it.split(splitter)
                    .map { string -> string.trim() }
                    .filter (String::isNotBlank )
                    .map { string -> string.toInt() }
            }
            .toList()

    fun readFileTo2dArray(fileName: String) =
        readFile(fileName)
            .map { it.chunked(1).toTypedArray() }
            .toTypedArray()

    fun readFileTo2dArrayAndTranspose(fileName: String) =
        transposeArray(readFileTo2dArray(fileName))
}

fun transposeArray(input: Array<Array<String>>, times: Int = 1): Array<Array<String>> {
    require(times >= 0) { "times must be >= 0" }
    if (times == 0) return input

    var result = input
    repeat(times) {
        result = rotateArray(result)
    }
    return result
}

fun rotateArray(input: Array<Array<String>>): Array<Array<String>> {
    if (input.isEmpty()) return emptyArray()

    val maxRowLength = input.maxOfOrNull { it.size } ?: 0
    if (maxRowLength == 0) return Array(0) { emptyArray() }

    // Create a new rectangular array, padded with spaces for missing values.
    return Array(maxRowLength) { x ->
        Array(input.size) { y ->
            if (x < input[y].size) {
                input[y][x]
            } else {
                " " // Pad with a space
            }
        }
    }
}

fun transposeArray(input: Array<Array<Char>>): Array<Array<Char>> {
    if (input.isEmpty()) return emptyArray()

    val maxRowLength = input.maxOfOrNull { it.size } ?: 0
    if (maxRowLength == 0) return Array(0) { emptyArray() }

    return Array(maxRowLength) { x ->
        Array(input.size) { y ->
            if (x < input[y].size) {
                input[y][x]
            } else {
                ' ' // Pad with a space character
            }
        }
    }
}
