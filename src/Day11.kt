data class Monkey(
    val items: MutableList<Long>,
    val op: (Long) -> Long,
    val divBy: Int,
    val divByTrue: Int,
    val divByFalse: Int,
) {
    var inspectCount = 0
}

val testInput
    get() = listOf(
        Monkey(mutableListOf(79, 98), { it * 19 }, 23, 2, 3),
        Monkey(mutableListOf(54, 65, 75, 74), { it + 6 }, 19, 2, 0),
        Monkey(mutableListOf(79, 60, 97), { it * it }, 13, 1, 3),
        Monkey(mutableListOf(74), { it + 3 }, 17, 0, 1),
    )

val finalInput
    get() = listOf(
        Monkey(mutableListOf(71, 56, 50, 73), { it * 11 }, 13, 1, 7),
        Monkey(mutableListOf(70, 89, 82), { it + 1 }, 7, 3, 6),
        Monkey(mutableListOf(52, 95), { it * it }, 3, 5, 4),
        Monkey(mutableListOf(94, 64, 69, 87, 70), { it + 2 }, 19, 2, 6),
        Monkey(mutableListOf(98, 72, 98, 53, 97, 51), { it + 6 }, 5, 0, 5),
        Monkey(mutableListOf(79), { it + 7 }, 2, 7, 0),
        Monkey(mutableListOf(77, 55, 63, 93, 66, 90, 88, 71), { it * 7 }, 11, 2, 4),
        Monkey(mutableListOf(54, 97, 87, 70, 59, 82, 59), { it + 8 }, 17, 1, 3),
    )

fun main() {
    fun part1(monkeys: List<Monkey>): Long {
        repeat(20) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    monkey.inspectCount += 1
                    val item = monkey.items.removeFirst()
                    val level = monkey.op(item).floorDiv(3)
                    val toMonkey = when (level.rem(monkey.divBy) == 0L) {
                        true -> monkey.divByTrue
                        false -> monkey.divByFalse
                    }
                    monkeys[toMonkey].items.add(level)
                }
            }
        }
        return monkeys
            .sortedByDescending { it.inspectCount.toLong() }
            .take(2)
            .fold(1) { acc, monkey -> acc * monkey.inspectCount }
    }

    fun part2(monkeys: List<Monkey>): Long {
        val product = monkeys.fold(1) { acc, m -> acc * m.divBy }
        repeat(10000) {
            for (monkey in monkeys) {
                while (monkey.items.isNotEmpty()) {
                    monkey.inspectCount += 1
                    val item = monkey.items.removeFirst()
                    val level = monkey.op(item)
                    val toMonkey = when (level.rem(monkey.divBy) == 0L) {
                        true -> monkey.divByTrue
                        false -> monkey.divByFalse
                    }
                    monkeys[toMonkey].items.add(level.rem(product))
                }
            }
        }
        return monkeys
            .sortedByDescending { it.inspectCount }
            .take(2)
            .fold(1) { acc, monkey -> acc * monkey.inspectCount }
    }

    check(part1(testInput), 10605L)
    check(part2(testInput), 2713310158)

    println(part1(finalInput))
    println(part2(finalInput))
}
