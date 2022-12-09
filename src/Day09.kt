fun main() {
    fun isAdjacent(hx: Int, hy: Int, tx: Int, ty: Int): Boolean =
        tx in (hx - 1..hx + 1) && ty in (hy - 1..hy + 1)

    fun newPos(hx: Int, hy: Int, tx: Int, ty: Int): Pair<Int, Int> {
        if (isAdjacent(hx, hy, tx, ty)) return tx to ty
        val dx = if (hx > tx) 1 else if (hx == tx) 0 else -1
        val dy = if (hy > ty) 1 else if (hy == ty) 0 else -1
        return tx + dx to ty + dy
    }

    fun parse(input: List<String>): List<Pair<Char, Int>> =
        input.map {
            val (c, n) = it.split(" ")
            c.first() to n.toInt()
        }

    fun part1(input: List<String>): Int {
        var hx = 0
        var hy = 0
        var tx = 0
        var ty = 0
        val map = mutableSetOf<Pair<Int, Int>>()
        parse(input).forEach { (c, n) ->
            repeat(n) {
                when (c) {
                    'U' -> hy += 1
                    'D' -> hy -= 1
                    'R' -> hx += 1
                    'L' -> hx -= 1
                    else -> error(c)
                }
                val new = newPos(hx, hy, tx, ty)
                tx = new.first
                ty = new.second
                map.add(new)
            }
        }
        return map.size
    }

    fun part2(input: List<String>): Int {
        var hx = 0
        var hy = 0
        val knots = List(9) { Pair(0, 0) }.toMutableList()
        val map = mutableSetOf<Pair<Int, Int>>()
        parse(input).forEach { (c, n) ->
            repeat(n) {
                when (c) {
                    'U' -> hy += 1
                    'D' -> hy -= 1
                    'R' -> hx += 1
                    'L' -> hx -= 1
                    else -> error(c)
                }
                var prev = hx to hy
                for (index in knots.indices) {
                    val knot = knots[index]
                    val new = newPos(prev.first, prev.second, knot.first, knot.second)
                    knots[index] = new
                    prev = new
                }
                map.add(knots.last())
            }
        }
        return map.size
    }

    val test2 = """
        R 5
        U 8
        L 8
        D 3
        R 17
        D 10
        L 25
        U 20
    """.trimIndent().lines()
    val testInput = readInput("Day09_test")
    check(part1(testInput), 13)
    check(part2(testInput), 1)
    check(part2(test2), 36)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}
