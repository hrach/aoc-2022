import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun check(actual: Int, expected: Int) {
    check(actual == expected) { "Check failed. Received $actual"}
}

fun check(actual: String, expected: String) {
    check(actual == expected) { "Check failed. Received $actual"}
}

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt")
    .readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')
