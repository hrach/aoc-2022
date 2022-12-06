fun main() {
    fun part1(input: List<String>): Int {
        val m = input.first()
        m.windowedSequence(4, 1).forEachIndexed { index, s ->
            if (s.toSet().size == 4) return index + 4
        }
        error("e")
    }

    fun part2(input: List<String>): Int {
        val m = input.first()
        m.windowedSequence(14, 1).forEachIndexed { index, s ->
            if (s.toSet().size == 14) return index + 14
        }
        error("e")
    }

    val testInput = readInput("Day06_test")
    check(part1(listOf("mjqjpqmgbljsphdztnvjfqwrcgsmlb")), 7)
    check(part1(listOf("bvwbjplbgvbhsrlpgdmjqwftvncz")), 5)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}
