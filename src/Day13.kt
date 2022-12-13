fun main() {
	@Suppress("UNCHECKED_CAST")
	fun n(a: Any): List<Any> =
		if (a is List<*>) a as List<Any> else listOf(a)

	fun inOrder(li: Any, ri: Any): Boolean? {
		val ll = n(li).toMutableList()
		val rr = n(ri).toMutableList()
		while (ll.isNotEmpty() || rr.isNotEmpty()) {
			val l = ll.removeFirstOrNull() ?: return true
			val r = rr.removeFirstOrNull() ?: return false
			if (l is Int && r is Int) {
				if (l < r) return true
				if (l > r) return false
			} else {
				val inOrder = inOrder(l, r)
				if (inOrder != null) return inOrder
			}
		}
		return null
	}

	fun parseLine(line: String): Any {
		var i = 0
		val lists = mutableListOf<MutableList<Any>>()
		var current = mutableListOf<Any>()
		while (i < line.length) {
			when (line[i]) {
				'[' -> {
					current = mutableListOf()
					lists.lastOrNull()?.add(current)
					lists.add(current)
					i += 1
				}
				']' -> {
					lists.removeLast()
					if (lists.isEmpty()) return current
					current = lists.last()
					i += 1
				}
				',' -> {
					i += 1
				}
				else -> {
					val chars = line.substring(i).takeWhile { it != ',' && it != ']' }
					i += chars.length
					current.add(chars.toInt())
				}
			}
		}
		error("wrong structure")
	}

	fun parse(input: List<String>): List<Pair<Any, Any>> {
		return input.chunked(3).map {
			parseLine(it[0]) to parseLine(it[1])
		}
	}

	fun part1(input: List<String>): Int {
		val inputs = parse(input)
		var sums = 0
		inputs.forEachIndexed { index, (l, r) ->
			val inOrder = inOrder(l, r)!!
			if (inOrder) sums += index + 1
		}
		return sums
	}

	fun part2(input: List<String>): Int {
		val inputs = input
			.filter { it.isNotBlank() }
			.map { parseLine(it) } +
			listOf(listOf(listOf(2)), listOf(listOf(6)))

		val sorted = inputs.sortedWith { l, r ->
			when (inOrder(l, r)) {
				null -> 0
				true -> -1
				false -> 1
			}
		}
		val indexA = sorted.indexOf(listOf(listOf(2))) + 1
		val indexB = sorted.indexOf(listOf(listOf(6))) + 1
		return indexA * indexB
	}

	val testInput = readInput("Day13_test")
	check(part1(testInput), 13)
	check(part2(testInput), 140)

	val input = readInput("Day13")
	println(part1(input))
	println(part2(input))
}
