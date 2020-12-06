package day04
// By Sebastian Raaphorst, 2020.

import java.io.File

private val fields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val colors = Regex("""#[0-9a-fA-F]{6}""")
private val eyeColors = setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

typealias PassportData = String
typealias Passport = Map<String, String>

/**
 * We have a chunk of passport data that can span multiple lines.
 * Convert it into a map of keys to values.
 */
fun toPassport(data: PassportData): Passport =
    data.trim()
        .splitToSequence(' ', '\n')
        .map { keyval -> keyval.takeWhile { it != ':' } to keyval.takeLastWhile { it != ':' }}
        .toMap()

/**
 * Check if a passport is legal (i.e. it contains the required fields).
 */
private fun isLegalPassport(passport: Passport): Boolean =
    passport.keys.containsAll(fields)

/**
 * Parse passport data and check if it represents a legal passport.
 */
fun isLegalPassportData(data: PassportData): Boolean =
    isLegalPassport(toPassport(data))

/**
 * Parse passport data and check if it represents a valid passport.
 */
fun isValidatedPassportData(data: String): Boolean {
    fun legalRange(value: String, lowerBound: Int, upperBound: Int): Boolean {
        val int = value.toIntOrNull()
        return int != null && lowerBound <= int && int <= upperBound
    }

    val passport = toPassport(data)
    return isLegalPassport(passport) && passport.all { (key, value) -> when (key) {
        "byr" -> legalRange(value, 1920, 2002)
        "iyr" -> legalRange(value, 2010, 2020)
        "eyr" -> legalRange(value, 2020, 2030)
        "hgt" -> when {
            value.endsWith("cm") -> legalRange(value.dropLast(2), 150, 193)
            value.endsWith("in") -> legalRange(value.dropLast(2), 59, 76)
            else -> false
        }
        "hcl" -> colors.matches(value)
        "ecl" -> eyeColors.contains(value)
        "pid" -> value.all { it.isDigit() } && value.length == 9
        "cid" -> true
        else -> false
    } }
}


fun main() {
    // Answer 1: 245
    // Answer 2: 133
    val data = File("src/main/kotlin/day04/input.txt").readText().split(Regex("""\s{2}"""))
    println(data.count { isLegalPassportData(it) })
    println(data.count { isValidatedPassportData(it) })
}