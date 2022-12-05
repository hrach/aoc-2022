@file:Suppress("NAME_SHADOWING")

fun main() {
    data class Command(
        val amount: Int,
        val from: Int,
        val to: Int,
    )
    fun parse(input: List<String>): Pair<List<MutableList<Char>>, List<Command>> {
        val input = input.toMutableList()
        val state = MutableList(
            ((input[0].length + 1) / 4) + 1
        ) { mutableListOf<Char>() }
        while (input[0].isNotBlank()) {
            if (input[0].contains("[").not()) {
                input.removeAt(0)
                break
            }
            repeat(state.size) {
                val c = input[0].getOrNull(it * 4 + 1) ?: ' '
                if (c != ' ') {
                    state[it].add(c)
                }
            }
            input.removeAt(0)
        }
        input.removeAt(0)

        val commands = input
            .map {
                val numbers = "\\d+".toRegex().findAll(it).toList()
                Command(
                    amount = numbers[0].value.toInt(),
                    from = numbers[1].value.toInt(),
                    to = numbers[2].value.toInt(),
                )
            }
        state.forEach { it.reverse() }
        return state to commands
    }

    fun part1(input: List<String>): String {
        val (state, command) = parse(input)
        command.forEach {
            val from = state[it.from - 1]
            val to = state[it.to - 1]
            val what = from.takeLast(it.amount)
            repeat(it.amount) { from.removeLast() }
            to.addAll(what.reversed())
        }
        return state.mapNotNull { it.lastOrNull() }.joinToString("")
    }

    fun part2(input: List<String>): String {
        val (state, command) = parse(input)
        command.forEach {
            val from = state[it.from - 1]
            val to = state[it.to - 1]
            val what = from.takeLast(it.amount)
            repeat(it.amount) { from.removeLast() }
            to.addAll(what)
        }
        return state.mapNotNull { it.lastOrNull() }.joinToString("")
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput), "CMZ")

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}
