import utils.mapWithHistory
import utils.parseString
import utils.println
import utils.readInput
import utils.within
import kotlin.collections.emptySet

fun main() {
    fun part1(input: List<String>): Int {
        val space = input.indexOfFirst { it.isEmpty() }
        val fresh = input.subList(0, space)
        val ranges = fresh.map { LongRange.parseString(it) }
        return input.drop(space + 1).count { available ->
            val num = available.toLong()
            ranges.any { it.contains(num) }
        }
    }

    fun part2(input: List<String>): Long {
        val ranges = input
            .takeWhile { !it.isEmpty() }
            .asSequence()
            .map { LongRange.parseString(it) }
            .mapWithHistory { history, item ->
                when {
                    history.isEmpty() -> item
                    history.find { item.within(it) } != null -> LongRange.EMPTY
                    else -> {
                        val start = item.first
                        val end = item.last
                        var newStart = start
                        while (newStart < end) {
                            val range = history.find { newStart.within(it) }
                            if (range != null) {
                                newStart = range.last + 1
                            } else {
                                break
                            }
                        }
                        var newEnd = end
                        while (newEnd > newStart) {
                            val range = history.find { newEnd.within(it) }
                            if (range != null) {
                                newEnd = range.first - 1
                            } else {
                                break
                            }
                        }
                        when {
                            newStart > newEnd -> LongRange.EMPTY
                            else -> LongRange(newStart, newEnd)
                        }
                    }
                }
            }

//        val filtered = ranges.filter { range ->
//            ranges.find {  range != it && range.within(it) } != null
//        }
//        filtered.forEach { range ->
//            filtered.find {  range != it && range.within(it) }?.let {
//                println("Found $range within $it")
//            }
//        }

        return ranges.sumOf {
            it.last - it.first + 1
        }
    }

//    check(part1(listOf("test_input")) == 1)

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
//    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
