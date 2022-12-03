enum class G(val score: Int) {
	Rock(1),
	Paper(2),
	Scissor(3),
}

fun main() {
	fun points(b: G, a: G): Int {
		return when {
			a == b -> 3
			(a == G.Rock && b == G.Scissor) || (a == G.Paper && b == G.Rock) || (a == G.Scissor && b == G.Paper) -> 6
			else -> 0
		}
	}

	fun part1(input: List<String>): Int {
		return input
			.filter { it.isNotBlank() }
			.sumOf {
				val a = when (it[0]) {
					'A' -> G.Rock
					'B' -> G.Paper
					'C' -> G.Scissor
					else -> error(input)
				}
				val b = when (it.get(2)) {
					'X' -> G.Rock
					'Y' -> G.Paper
					'Z' -> G.Scissor
					else -> error(input)
				}
				points(a, b) + b.score
			}
	}

	fun part2(input: List<String>): Int {
		return input
			.filter { it.isNotBlank() }
			.also(::println)
			.sumOf {
				println(it)
				val a = when (it[0]) {
					'A' -> G.Rock
					'B' -> G.Paper
					'C' -> G.Scissor
					else -> error(input)
				}
				when (it[2]) {
					'X' -> 0 + when (a) {
						G.Rock -> G.Scissor
						G.Paper -> G.Rock
						G.Scissor -> G.Paper
					}.score

					'Y' -> 3 + a.score
					'Z' -> 6 + when (a) {
						G.Rock -> G.Paper
						G.Paper -> G.Scissor
						G.Scissor -> G.Rock
					}.score

					else -> error(input)
				}
			}
	}

	val testInput = readInput("Day02_test")
	check(part1(testInput), 15)

	val input = readInput("Day02")
	println(part1(input))
	println(part2(input))
}
