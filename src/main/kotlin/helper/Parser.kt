package at.kocmana.helper

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
            .map { it.split(splitter) }
            .map { it.map(Integer::valueOf) }
            .toList()

    fun readFileTo2dArray(fileName: String) =
        readFile(fileName)
            .map { it.chunked(1).toTypedArray() }
            .toTypedArray()

    fun readFileTo2dArrayAndTranspose(fileName: String) =
        transposeArray(readFileTo2dArray(fileName))
}

fun transposeArray(input: Array<Array<String>>): Array<Array<String>> =
    Array(input[0].size) { x ->
        Array(input.size) { y ->
            input[y][x]
        }
    }

fun transposeArray(input: Array<Array<Char>>): Array<Array<Char>> =
    Array(input[0].size) { x ->
        Array(input.size) { y ->
            input[y][x]
        }
    }
