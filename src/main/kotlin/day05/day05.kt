package day05
// By Sebastian Raaphorst, 2020.

import java.io.File

private enum class Half {
    Lower,
    Upper
}

/**
 * A generic binary search where a string is used to select the half of the range.
 */
private tailrec fun binarySearch(map: (Char) -> Half, input: String, lowerBound: Int, upperBound: Int): Int? {
    if (input.isEmpty())
        return if (lowerBound == upperBound)
            lowerBound
        else
            null

    return when (map(input.first())) {
        Half.Lower -> binarySearch(map, input.drop(1), lowerBound, (lowerBound + upperBound) shr 1)
        Half.Upper -> binarySearch(map, input.drop(1), ((lowerBound + upperBound) shr 1) + 1, upperBound)
    }
}

/**
 * Given the data on a boarding pass, calculate the seat ID.
 */
fun calculateID(data: String): Int? {
    val row = binarySearch({ when (it) {
        'F' -> Half.Lower
        'B' -> Half.Upper
        else -> throw IllegalArgumentException("Illegal row character: $it")
    } }, data.take(7), 0, 127)
    assert(row in 0 until 128)

    val col = binarySearch({when (it) {
        'L' -> Half.Lower
        'R' -> Half.Upper
        else -> throw IllegalArgumentException("Illegal column character: $it")
    } }, data.drop(7), 0, 7)
    assert(col in 0 until 8)

    return if (row == null || col == null) null else 8 * row + col
}

/**
 * Find the missing ID in the range.
 */
fun findMissingID(data: List<String>): Int? {
    val seats = data.mapNotNull{ calculateID(it) }.sorted()
    return seats.withIndex().find { (idx, id) -> id - idx != seats.first() }?.let { it.component2() - 1 }
}

fun main() {
    // Answer 1: 930
    // Answer 2: 515
    val data = File("src/main/kotlin/day05/input.txt").readLines()
    println(data.mapNotNull { calculateID(it) }.maxOrNull())
    println(findMissingID(data))
}