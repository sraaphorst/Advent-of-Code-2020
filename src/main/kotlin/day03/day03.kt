package day03
// By Sebastian Raaphorst, 2020.

import java.io.File

typealias Trees = List<List<Boolean>>

/**
 * Convert a hill, which consists of lines consisting of # (tree) and . (blank space)
 * into a list of rows indicating the positions of trees.
 */
private fun List<CharArray>.toTrees(): List<List<Boolean>> =
    this.map { it -> it.map { it == '#' } }

/**
 * Given a hill defined by the position of its trees (True = tree, False = empty space), a starting position of
 * (0, col), and a movement of (deltaRow, deltaCol), determine how many trees are hit.
 */
tailrec
fun treesHit(trees: Trees, col: Int = 0, deltaCol: Int = 3, deltaRow: Int = 1, hits: Long = 0): Long =
    when {
        trees.isEmpty() -> hits
        else -> treesHit(trees.drop(deltaRow), (col + deltaCol) % trees.first().size, deltaCol, deltaRow,
            hits + if (trees.first()[col]) 1L else 0L)
    }

fun productTreesHit(trees: Trees, slopes: List<Pair<Int, Int>>): Long =
    slopes.map { treesHit(trees, deltaCol = it.first, deltaRow= it.second) }.reduce(Long::times)

fun main() {
    // Answer 1: 159
    // Answer 2: 6419669520
    val trees = File("src/main/kotlin/day03/input.txt").readLines().map { it.toCharArray() }.toTrees()
    println(treesHit(trees))
    println()
    println(productTreesHit(trees, listOf(Pair(1, 1), Pair(3, 1), Pair(5, 1), Pair(7, 1), Pair(1, 2))))
}