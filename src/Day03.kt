import utils.println
import utils.readInput
import kotlin.math.min

fun main() {
    fun findMaxJoltage(value: String, length: Int): Long {
        var minIndex = 0
        var maxIndex = value.length - length
        val maxValue = StringBuilder()
        repeat(length) {
            val part = value.toCharArray(minIndex, maxIndex + 1) // exclusive upper bound
            val maxNum = part.max()
            maxValue.append(maxNum)
            minIndex += part.indexOf(maxNum) + 1
            maxIndex++
        }
        return maxValue.toString().toLong()
    }

    fun part1(input: List<String>): Long {
        return input.sumOf {
            findMaxJoltage(it, 2)
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf {
            findMaxJoltage(it, 12)
        }
    }

//    check(part1(listOf("test_input")) == 1)

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
