fun main() {
	fun part1(input: List<String>): Int {
		return input
			.filter { it.isNotBlank() }
			.map {
				val (a, b) = it.split(",")
				val (aa, ab) = a.split("-").map { it.toInt() }
				val (ba, bb) = b.split("-").map { it.toInt() }
				IntRange(aa, ab) to IntRange(ba, bb)
			}
			.count { (a, b) ->
				(a.first in b && a.last in b) ||
					(b.first in a && b.last in a)
			}
	}

	fun part2(input: List<String>): Int {
		return input
			.filter { it.isNotBlank() }
			.map {
				val (a, b) = it.split(",")
				val (aa, ab) = a.split("-").map { it.toInt() }
				val (ba, bb) = b.split("-").map { it.toInt() }
				IntRange(aa, ab) to IntRange(ba, bb)
			}
			.count { (a, b) ->
				(a.first in b || a.last in b) ||
					(b.first in a || b.last in a)
			}
	}

	val testInput = readInput("Day04_test")
	check(part1(testInput), 2)

	val input = readInput("Day04")
	println(part1(input))
	println(part2(input))
}
