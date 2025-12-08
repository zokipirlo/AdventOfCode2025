import utils.Map2d
import utils.println
import utils.readInput

fun main() {
    fun part1(input: List<String>): Int {
        val map2d = Map2d.CharMap2d(input)
        return map2d.itemIterable().count { item ->
            when (item.item) {
                '@' -> map2d.allSides(item, true).count { it?.item == '@' } < 4
                else -> false
            }
        }
    }

    fun part2(input: List<String>): Int {
        val map2d = Map2d.CharMap2d(input)
        var count = 0
        while (true) {
            val toRemove = map2d.itemIterable().filter { item ->
                when (item.item) {
                    '@' -> map2d.allSides(item, true).count { it?.item == '@' } < 4
                    else -> false
                }
            }
            if (toRemove.count() == 0) break
            count += toRemove.count()
            map2d.updateValues { y, x, item ->
                toRemove.find { it.y == y && it.x == x }?.let { '.' } ?: item
            }
        }
        return count
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
