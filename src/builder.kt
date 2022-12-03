import java.io.File
import java.net.URL

fun main() {
	val i = "1"
	val url = "https://adventofcode.com/2022/day/$i/input"
	val cookie = "session=${File("src/cookie.txt").readText().trim()}"
	val taskInput = URL(url).openConnection().apply {
		setRequestProperty("Cookie", cookie)
	}.getInputStream().buffered().reader().readText()

	val iPadded = i.padStart(2, '0')
	val script = File("src/builder_template.kt").readText().replace("01", iPadded)
	File("src/Day${iPadded}_test.txt").outputStream().use { }
	File("src/Day${iPadded}.txt").outputStream().use {
		it.bufferedWriter().use {
			it.write(taskInput)
		}
	}
	File("src/Day${iPadded}.kt").outputStream().use {
		it.bufferedWriter().use {
			it.write(script)
		}
	}
}
