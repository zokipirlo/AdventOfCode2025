import utils.*

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

    fun inAnyRange(items: List<LongRange>, item: LongRange) = items.find { item != it && item.within(it) } != null

    fun filterList(items: List<LongRange>): Pair<Boolean, List<LongRange>> {
        var anyChange = false
        val newList = items.mapWithHistoryNotNull { history, item ->
            when {
                inAnyRange(history, item) -> {
                    println("Item $item is in the range, removing")
                    anyChange = true
                    null
                }
                else -> {
                    val start = item.first
                    val end = item.last

                    val startRange = history.find { item != it && start.within(it) }
                    val endRange = history.find { item != it && end.within(it) }

                    when {
                        startRange == null && endRange == null -> {
                            println("Item $item not found, keeping")
                            item
                        }
                        startRange != null && endRange != null -> {
                            anyChange = true
                            println("Item $item between two, $startRange and $endRange")
                            LongRange(startRange.last + 1, endRange.first - 1)
                        }
                        startRange != null -> {
                            anyChange = true
                            println("Item $item in start range, $startRange")
                            LongRange(startRange.last + 1, end)
                        }
                        endRange != null -> {
                            anyChange = true
                            println("Item $item in end range, $endRange")
                            LongRange(start, endRange.first - 1)
                        }
                        else -> {
                            throw IllegalStateException("Should not happen")
                        }
                    }
                }
            }
        }.sortedByDescending { it.size() }.sortedBy { it.first }
        return Pair(anyChange, newList)
    }

    fun part2(input: List<String>): Long {
        val ranges = input
            .takeWhile { !it.isEmpty() }
            .map { LongRange.parseString(it) }
            .sortedByDescending { it.size() }
            .sortedBy { it.first }

        println("Ranges: $ranges")
        var (modified, filtered) = filterList(ranges)
        while (modified) {
            println("Filtering list")
            val res = filterList(filtered)
            modified = res.first
            filtered = res.second
        }

        filtered.forEach { range ->
            filtered.find {  range != it && range.within(it) }?.let {
                println("Found $range within $it")
            }
        }

        println(filtered)

        return filtered.sumOf {
            it.size()
        }
    }

//    check(part1(listOf("test_input")) == 1)

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    part1(input).println()
    part2(input).println()
}
