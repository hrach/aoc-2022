fun main() {
    data class Map(
        val levels: List<List<Int>>,
        val start: Pair<Int, Int>,
        val end: Pair<Int, Int>,
    ) {
        fun getSteps(current: Pair<Int, Int>): List<Pair<Int, Int>> {
            return listOf(
                current.first - 1 to current.second,
                current.first to current.second - 1,
                current.first to current.second + 1,
                current.first + 1 to current.second,
            ).mapNotNull {
                val level = levels.getOrNull(it.first)?.getOrNull(it.second) ?: return@mapNotNull null
                if ((level - levels[current.first][current.second]) <= 1) {
                    it
                } else {
                    null
                }
            }
        }
    }

    fun parse(input: List<String>): Map {
        var start: Pair<Int, Int>? = null
        var end: Pair<Int, Int>? = null
        val levels = input.mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                when (c) {
                    'S' -> {
                        start = y to x
                        'a'.code
                    }

                    'E' -> {
                        end = y to x
                        'z'.code
                    }

                    else -> c.code
                }
            }
        }
        return Map(levels, start!!, end!!)
    }

    fun shortest(map: Map): Int {
        var todo = listOf(map.start to 0)
        val visited = mutableSetOf(map.start)
        while (todo.isNotEmpty()) {
            val (pos, price) = todo.first()
            val steps = map.getSteps(pos).filter { it !in visited }
            for (step in steps) {
                visited += step
                if (step == map.end) return price + 1
            }
            todo = (todo.drop(1) + steps.map { it to price + 1 }).sortedBy { it.second }
        }
        return Int.MAX_VALUE
    }

    fun part1(input: List<String>): Int {
        val map = parse(input)
        return shortest(map)
    }

    fun part2(input: List<String>): Int {
        val map = parse(input)
        val lengths = mutableListOf<Int>()
        map.levels.forEachIndexed { y, row ->
            row.forEachIndexed q@{ x, c ->
                if (c != 'a'.code) return@q
                val newMap = map.copy(start = y to x)
                lengths.add(shortest(newMap))
            }
        }
        return lengths.min()
    }

    val testInput = readInput("Day12_test")
    check(part1(testInput), 31)
    check(part2(testInput), 29)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}
