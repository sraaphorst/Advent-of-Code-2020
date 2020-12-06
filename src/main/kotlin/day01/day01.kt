package day01

import java.io.File

private val Sum = 2020

/**
 * Find two numbers in expenseList that sum to 2020 and return their product.
 */
fun expenses(expenseList: List<Int>): Int? {
    val hs = HashSet(expenseList)
    return hs.find{ hs.contains(Sum - it) }?.let { (Sum - it) * it }
}

/**
 * Given a collection (c0, c1, c2, ...), generate all pairs:
 * (c0, c1), (c0, c2), ..., (c1, c2), ...
 * If lst is infinite, since we use this ordering, we will never get to (c1, c2).
 */
private inline fun <reified T> allPairs(lst: Collection<T>) = sequence {
    val arr = lst.toTypedArray()
    for (idx0 in 0 until (arr.size - 1))
        for (idx1 in idx0 until arr.size)
            yield(Pair(arr[idx0], arr[idx1]))
}

/**
 * Find three numbers in the expenseList that sum to 2020 and return their product.
 */
fun expenses2(expenseList: List<Int>): Int? {
    val hs = HashSet(expenseList)
    return allPairs(expenseList).find { hs.contains(Sum - it.first - it.second) }
        ?.let{ (Sum - it.first - it.second) * it.first * it.second}
}

fun main() {
    // Answer1: 471019
    // Answer2: 103927824
    val data = File("src/main/kotlin/day01/input.txt").readLines().map { it.toInt() }
    println(expenses(data))
    println(expenses2(data))
}