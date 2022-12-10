sealed interface Command {
    data class Addx(val n: Int) : Command
    object Noop : Command
}

fun main() {
    fun parse(input: List<String>): List<Command> =
        input
            .filter { it.isNotBlank() }
            .map {
                when (it.substring(0, 4)) {
                    "addx" -> Command.Addx(it.substring(4).trim().toInt())
                    "noop" -> Command.Noop
                    else -> error(it)
                }
            }

    fun part1(input: List<String>): Int {
        val commands = parse(input).toMutableList()
        var x = 1
        var cycle = 1
        var command: Command? = null
        var commandCounter = 0
        var sum = 0
        while (commands.isNotEmpty()) {
            if (command == null) {
                command = commands.removeFirst()
                commandCounter = if (command is Command.Addx) 2 else 1
            }

            commandCounter -= 1
            cycle += 1

            if (commandCounter == 0) {
                when (command) {
                    is Command.Addx -> x += command.n
                    is Command.Noop -> {}
                }
                command = null
            }
            if (cycle.rem(40) == 20) {
                sum += (cycle * x)
            }
        }
        return sum
    }

    fun part2(input: List<String>): String {
        val lines = mutableListOf<String>()
        var line = ""
        val commands = parse(input).toMutableList()
        var x = 1
        var sprite = x..x+2

        var cycle = 1
        var command: Command? = null
        var commandCounter = 0

        while (commands.isNotEmpty()) {
            val pos = cycle.rem(40)
            line += if (pos in sprite) "#" else "."
            if (cycle.rem(40) == 0) {
                lines.add(line)
                line = ""
            }

            if (command == null) {
                command = commands.removeFirst()
                commandCounter = if (command is Command.Addx) 2 else 1
            }

            commandCounter -= 1
            cycle += 1

            if (commandCounter == 0) {
                when (command) {
                    is Command.Addx -> {
                        x += (command as Command.Addx).n
                        sprite = x..x+2
                    }
                    is Command.Noop -> {}
                    null -> error("e")
                }
                command = null
            }
        }
        return lines.joinToString("\n")
    }

    val testInput = readInput("Day10_test")
    check(part1(testInput), 13140)
    println(part2(testInput))

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}
