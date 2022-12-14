class Map {
    val points = mutableMapOf<Pair<Int, Int>, Char>()

    @Suppress("unused")
    fun print() {
        val minX = points.keys.minBy { it.first }.first
        val maxX = points.keys.maxBy { it.first }.first
        val minY = points.keys.minBy { it.second }.second
        val maxY = points.keys.maxBy { it.second }.second
        for (y in minY..maxY) {
            for (x in minX..maxX) {
                print(points[x to y] ?: '.')
            }
            print("\n")
        }
    }
}

fun main() {
    fun parse(input: List<String>): Map {
        val map = Map()
        input.forEach { line ->
            val points = line
                .split(" -> ")
                .map { it.split(",").map { it.toInt() } }
            points.zipWithNext().forEach { (to, from) ->
                val xx = minOf(from[0], to[0])..maxOf(from[0], to[0])
                val yy = minOf(from[1], to[1])..maxOf(from[1], to[1])
                for (x in xx) for (y in yy) map.points[x to y] = '#'
            }
        }
        return map
    }

    fun next(map: Map, point: Pair<Int, Int>, bottom: Int? = null): Pair<Int, Int> {
        if (bottom != null && point.second + 1 >= bottom) return point
        val down = point.copy(second = point.second + 1)
        if (map.points.get(down) == null) return down
        val downLeft = point.copy(point.first - 1, point.second + 1)
        if (map.points.get(downLeft) == null) return downLeft
        val downRight = point.copy(point.first + 1, point.second + 1)
        if (map.points.get(downRight) == null) return downRight
        return point
    }

    fun part1(input: List<String>): Int {
        val map = parse(input)
        val bottom = map.points.keys.maxBy { it.second }.second
        var pos = 500 to 0
        var snowFlakes = 1
        while (pos.second <= bottom) {
            val newPos = next(map, pos)
            if (newPos == pos) {
                map.points[newPos] = 'o'
                pos = 500 to 0
                snowFlakes += 1
            } else {
                pos = newPos
            }
        }
        return snowFlakes - 1
    }

    fun part2(input: List<String>): Int {
        val map = parse(input)
        val bottom = map.points.keys.maxBy { it.second }.second + 2
        var pos = 500 to 0
        var snowFlakes = 1
        while (true) {
            val newPos = next(map, pos, bottom)
            if (newPos == 500 to 0) break
            if (newPos == pos) {
                map.points[newPos] = 'o'
                pos = 500 to 0
                snowFlakes += 1
            } else {
                pos = newPos
            }
        }
        return snowFlakes
    }

    val testInput = readInput("Day14_test")
    check(part1(testInput), 24)
    check(part2(testInput), 93)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}
