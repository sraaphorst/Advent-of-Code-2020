package day02
// By Sebastian Raaphorst, 2020.

import java.io.File

private val re = Regex("""(\d+)-(\d+) (\w): (.*)""")

/**
 * Given a line of the form:
 * n1-n2 c: password
 * Ensure that the character c appears between n1 and n2 times in password.
 */
fun checkPassword1(line: String): Boolean =
    re.matchEntire(line)
        ?.destructured
        ?.let { (minctStr, maxctStr, chStr, password) ->
            val minct = minctStr.toInt()
            val maxct = maxctStr.toInt()
            val ch = chStr.first()
            assert(minct <= maxct)
            password.count { it == ch } in (minct..maxct)
        } ?: false

/**
 * Given a line of the form:
 * n1-n2 c: password
 * Ensure that the character c appears either in position n1 or n2 but not both in password.
 * Note we offset by 1 and not by the usual 0.
 */
fun checkPassword2(line: String): Boolean =
    re.matchEntire(line)
        ?.destructured
        ?.let { (pos1Str, pos2Str, chStr, password) ->
            val pos1 = pos1Str.toInt() - 1
            val pos2 = pos2Str.toInt() - 1
            val ch = chStr.first()
            listOf(password[pos1], password[pos2]).count { it == ch } == 1
        } ?: false

fun main() {
    // Answer1: 550
    // Answer2: 634
    val data = File("src/main/kotlin/day02/input.txt").readLines()
    println(data.map { checkPassword1(it) }.count { it })
    println(data.map { checkPassword2(it) }.count { it })
}
