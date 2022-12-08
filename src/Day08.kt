@file:Suppress("NAME_SHADOWING")

fun main() {
    fun getSeq(input: List<List<Int>>, y: Int, x: Int, dy: Int, dx: Int): Sequence<Int> = sequence {
        var x = x
        var y = y
        do {
            y += dy
            x += dx
            val t = input.getOrNull(y)?.getOrNull(x)
            if (t != null) yield(t)
        } while (t != null)
    }

    fun Sequence<Int>.customAny(t: Int): Boolean {
        if (this.firstOrNull() == null) return true
        return find { it >= t } == null
    }

    fun part1(input: List<String>): Int {
        val input = input.map { it.toCharArray().map { it.toString().toInt() } }
        var counter = 0
        val map = mutableMapOf<Int, MutableMap<Int, Int>>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, t ->
                val up = getSeq(input, y, x, -1, 0).customAny(t)
                val left = getSeq(input, y, x, 0, -1).customAny(t)
                val right = getSeq(input, y, x, 0, +1).customAny(t)
                val bottom = getSeq(input, y, x, +1, 0).customAny(t)

                val isVisible = up || left || right || bottom
                if (isVisible) {
                    counter += 1
                }
                map.getOrPut(y) { mutableMapOf<Int, Int>() }
                map[y]!![x] = if (isVisible) 1 else 0
            }
        }
        return counter
    }

    fun Sequence<Int>.score(t: Int): Int {
        if (firstOrNull() == null) return 0
        val taken = takeWhile { it < t }
        return if (taken.count() == count()) taken.count()
        else taken.count() + 1
    }

    fun part2(input: List<String>): Int {
        val input = input.map { it.toCharArray().map { it.toString().toInt() } }
        val map = mutableMapOf<Int, MutableMap<Int, Int>>()
        input.forEachIndexed { y, row ->
            row.forEachIndexed { x, t ->
                val up = getSeq(input, y, x, -1, 0).score(t)
                val left = getSeq(input, y, x, 0, -1).score(t)
                val right = getSeq(input, y, x, 0, +1).score(t)
                val bottom = getSeq(input, y, x, +1, 0).score(t)
                val score = up * left * right * bottom
                map.getOrPut(y) { mutableMapOf<Int, Int>() }
                map[y]!![x] = score
            }
        }
        return map.values.flatMap { it.values }.max()
    }

    val testInput = readInput("Day08_test")
    check(part1(testInput), 21)
    check(part2(testInput), 8)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}
