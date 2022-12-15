import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlin.math.absoluteValue

data class Point(val x: Int, val y: Int)

inline fun d2(ax: Int, ay: Int, b: Point): Int {
	return (ax - b.x).absoluteValue + (ay - b.y).absoluteValue
}

suspend fun main() {
	val regexp = "-?\\d+".toRegex()

	fun parse(input: List<String>): List<Pair<Point, Point>> {
		return input.map { line ->
			val nums = regexp.findAll(line).toList().map { it.value.toInt() }
			Point(nums[0], nums[1]) to Point(nums[2], nums[3])
		}
	}

	fun d(a: Point, b: Point): Int {
		return (a.x - b.x).absoluteValue + (a.y - b.y).absoluteValue
	}

	fun calc(data: List<Pair<Point, Point>>, n: Int): Set<Point> {
		val positions = mutableSetOf<Point>()
		data.forEach { (sensor, beacon) ->
			val maxD = d(sensor, beacon)
			var x = sensor.x
			var d = 0
			while (d <= maxD) {
				val p = Point(x = x, y = n)
				d = d(p, sensor)
				if (d <= maxD) positions.add(p)
				x -= 1
			}
			x = sensor.x
			d = 0
			while (d <= maxD) {
				val p = Point(x = x, y = n)
				d = d(p, sensor)
				if (d <= maxD) positions.add(p)
				x += 1
			}
		}
		data.forEach { (sensor, beacon) ->
			if (sensor.y == n) positions.remove(beacon)
			if (beacon.y == n) positions.remove(beacon)
		}
		return positions
	}

	@Suppress("ReplaceManualRangeWithIndicesCalls")
	fun calc2(data: List<Pair<Point, Point>>, n: Int, maxBounds: Int): ByteArray {
		val positions = ByteArray(maxBounds + 1)
		for (i in 0 until data.size) {
			val (sensor, beacon) = data[i]
			val maxD = d2(sensor.x, sensor.y, beacon)
			var x = sensor.x
			var d = 0
			while (d <= maxD && x >= 0 && x <= maxBounds) {
				d = d2(x, n, sensor)
				if (d <= maxD) positions[x] = 1
				x -= 1
			}
			x = sensor.x + 1
			d = 0
			while (d <= maxD && x >= 0 && x <= maxBounds) {
				d = d2(x, n, sensor)
				if (d <= maxD) positions[x] = 1
				x += 1
			}
			if (sensor.y == n && sensor.x >= 0 && sensor.x <= maxBounds) positions[sensor.x] = 1
			if (beacon.y == n && beacon.x >= 0 && beacon.x <= maxBounds) positions[beacon.x] = 1
		}
		return positions
	}

	fun part1(input: List<String>, n: Int): Int {
		val data = parse(input)
		return calc(data, n).size
	}

	fun work(data: List<Pair<Point, Point>>, i: Int, max: Int): Long {
		val res = calc2(data, i, max)
		val x = res.indexOf(0.toByte())
		if (x == -1) return -1L
		return i + x * 4000000L
	}

	suspend fun part2(input: List<String>, max: Int): Long = coroutineScope {
		val data = parse(input)
		var i = 0 // to speed up, set this to 2860700
		while (i <= max) {
			val tasksNum = 6
			val tasks = List(tasksNum) {
				async { work(data, i + it, max) }
			}
			tasks.map {
				val r = it.await()
				if (r != -1L) return@coroutineScope r
			}
			i += tasksNum
		}
		error("error")
	}

	val testInput = readInput("Day15_test")
	val input = readInput("Day15")
	check(part1(testInput, 10), 26)
	println(part1(input, 2000000))

//	check(part2(testInput, 20), 56000011L)
	println(part2(input, 4000000))
}
