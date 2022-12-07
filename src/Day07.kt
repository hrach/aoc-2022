sealed interface Node {
    val name: String
    val size: Int

    data class Dir(
        override val name: String,
        val nodes: MutableList<Node>,
        val parent: Dir?,
    ) : Node {
        override val size: Int
            get() {
                return nodes.sumOf { it.size }
            }
        val dirs: Sequence<Dir> = sequence {
            yield(this@Dir)
            yieldAll(nodes.filterIsInstance<Dir>().flatMap { it.dirs })
        }
    }

    data class File(
        override val name: String,
        override val size: Int,
    ) : Node
}

fun parseInput(input: List<String>): Node.Dir {
    val root = Node.Dir(nodes = mutableListOf(), name = "/", parent = null)
    var wd = root
    input.filter { it.isNotBlank() }.forEach { line ->
        when {
            line.startsWith("$ cd") -> {
                wd = getDir(root, wd, line.removePrefix("$ cd ").trim())
            }

            line.startsWith("$ ls") -> {
                // no-op
            }

            line.startsWith("dir ") -> {
                getDir(root, wd, line.removePrefix("dir ").trim())
            }

            else -> {
                val (size, name) = line.split(" ", limit = 2)
                wd.nodes.add(Node.File(size = size.toInt(), name = name.trim()))
            }
        }
    }
    return root
}

fun getDir(root: Node.Dir, wd: Node.Dir, path: String): Node.Dir {
    return when (path) {
        "/" -> root
        ".." -> wd.parent!!
        else -> {
            if (wd.nodes.none { it is Node.Dir && it.name == path }) {
                wd.nodes.add(
                    Node.Dir(nodes = mutableListOf(), name = path, parent = wd)
                )
            }
            wd.nodes.filterIsInstance<Node.Dir>().first { it.name == path }
        }
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val tree = parseInput(input)
        return tree.dirs.filter { it.size <= 100000 }.sumOf { it.size }
    }

    fun part2(input: List<String>): Int {
        val tree = parseInput(input)
        val needed = (tree.size - (70000000 - 30000000))
        return tree.dirs.filter { it.size >= needed }.minOf { it.size }
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput), 95437)
    check(part2(testInput), 24933642)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}
