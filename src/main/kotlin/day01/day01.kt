package day01

import java.io.File

/**
 * Find two numbers in expenseList that sum to 2020 and return their product.
 */
fun expenses(expenseList: List<Int>): Int? {
    val hs = HashSet(expenseList)
    return hs.find{ hs.contains(2020 - it) }?.let { (2020 - it) * it }
}

/**
 * Given a collection (c0, c1, c2, ...), generate all pairs:
 * (c0, c1), (c0, c2), ..., (c1, c2), ...
 * If lst is infinite, since we use this ordering, we will never get to (c1, c2).
 */
inline fun <reified T> allPairs(lst: Collection<T>) = sequence {
    if (lst.size > 1) {
        val arr = lst.toTypedArray()
        var idx0 = 0
        var idx1 = 1

        while (idx0 < arr.size - 1) {
            if (idx1 >= arr.size) {
                idx0 += 1
                idx1 = idx0 + 1
            }
            if (idx1 < arr.size)
                yield(Pair(arr[idx0], arr[idx1]))
            idx1 += 1
        }
    }
}

fun expenses2(expenseList: List<Int>): Int? {
    val hs = HashSet(expenseList)
    return allPairs(expenseList).find { hs.contains(2020 - it.first - it.second) }
        ?.let{ (2020 - it.first - it.second) * it.first * it.second}
}

fun main() {
    // Answer1: 471019
    // Answer2: 103927824
    println(expenses(File("src/main/kotlin/day01/input.txt").readLines().map { it.toInt() }))
    println(expenses2(File("src/main/kotlin/day01/input.txt").readLines().map { it.toInt() }))
}