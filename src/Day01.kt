fun main() {
	fun part1(input: List<String>): Int {
		val groups = mutableListOf<List<Int>>()
		var group = mutableListOf<Int>()
		input.forEach {
			if (it.isBlank()) group = mutableListOf<Int>().also { groups.add(it) }
			else group.add(it.toInt())
		}
		return groups.maxOf { it.sum() }
	}

	fun part2(input: List<String>): Int {
		val groups = mutableListOf<List<Int>>()
		var group = mutableListOf<Int>()
		input.forEach {
			if (it.isBlank()) group = mutableListOf<Int>().also { groups.add(it) }
			else group.add(it.toInt())
		}
		return groups.map { it.sum() }.sortedDescending().take(3).sum()
	}

	val testInput = readInput("Day01_test")
	check(part1(testInput), 24000)

	val input = readInput("Day01")
	println(part1(input))
	println(part2(input))
}
