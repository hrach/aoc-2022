fun main() {
	fun part1(input: List<String>): Int {
		return input
			.sumOf {
				val l = it.length / 2
				val a = it.substring(0, l).toSet()
				val b = it.substring(l).toSet()
				val shared = a.intersect(b).first()
				if (shared.isLowerCase()) {
					shared.code - 96
				} else {
					shared.code - 64 + 26
				}
			}
	}

	fun part2(input: List<String>): Int {
		return input
			.chunked(3)
			.sumOf {
				val i = it.map { it.toSet() }.reduce { a: Set<Char>, b -> a.intersect(b) }
				val shared = i.first()
				if (shared.isLowerCase()) {
					shared.code - 96
				} else {
					shared.code - 64 + 26
				}
			}
	}

	val testInput = readInput("Day03_test")
	check(part1(testInput), 157)

	val input = readInput("Day03")
	println(part1(input))
	println(part2(input))
}
