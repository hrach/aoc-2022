import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

fun check(actual: Int, expected: Int) {
    check(actual == expected) { "Check failed. Received $actual"}
}

fun check(actual: Long, expected: Long) {
    check(actual == expected) { "Check failed. Received $actual"}
}

fun check(actual: String, expected: String) {
    check(actual == expected) { "Check failed. Received $actual"}
}

fun lcm(n1: Int, n2: Int): Int {
    var lcm = if (n1 > n2) n1 else n2

    // Always true
    while (true) {
        if (lcm.rem(n1) == 0 && lcm.rem(n2) == 0) {
            break
        }
        ++lcm
    }

    return lcm
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
