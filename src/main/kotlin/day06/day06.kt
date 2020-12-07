package day06
// By Sebastian Raaphorst, 2020.

import java.io.File

/**
 * Count the number of yeses in a group, i.e. the size of the union of all the lines representing the group.
 */
private fun numYesInGroup(group: String): Int =
    group.filter { it != '\n' }.toSet().size

/**
 * Count the number of people who all answered yes to a question in a group, i.e. the size of the intersection of all
 * the lines representing the group.
 */
private fun numAllYesInGroup(group: String): Int =
    group.trim()
        .split('\n')
        .map(String::toSet)
        .reduceRight(Set<Char>::intersect).size

fun main() {
    // Answer 1: 6680
    // Answer 2: 3117
    val data = File("src/main/kotlin/day06/input.txt").readText().split("\n\n")
    println(data.map { numYesInGroup(it) }.sum())
    println(data.map { numAllYesInGroup(it) }.sum())
}