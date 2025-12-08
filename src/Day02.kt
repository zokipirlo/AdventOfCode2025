import utils.println
import utils.readInput

fun main() {
    fun getAllNumbers(inputRange: String): LongRange {
        val (min, max) = inputRange.split("-")
        return LongRange(min.toLong(), max.toLong())
    }

    fun findSequence(number: Long): Long? {
        val stringNum = number.toString()
        if (stringNum.length % 2 != 0) {
            return null
        }
        val split = stringNum.length / 2
        val firstPart = stringNum.take(split)
        val secondPart = stringNum.takeLast(split)
        return when (firstPart == secondPart) {
            true -> number
            else -> null
        }
    }

    fun findAnySequence(number: Long): Long? {
        val stringNum = number.toString()
        val maxChunkSize = stringNum.length / 2
        (1..maxChunkSize).forEach { chunkSize ->
            val chunks = stringNum.chunked(chunkSize)
            val first = chunks.first()
            if (chunks.all { it == first }) {
                return number
            }
        }
        return null
    }

    fun part1(input: List<String>): Long {
       val hasSequence = input.first().split(",")
            .map { getAllNumbers(it) }
            .flatMap { it.mapNotNull { findSequence(it) } }
        return hasSequence.sum()
    }

    fun part2(input: List<String>): Long {
        val hasSequence = input.first().split(",")
            .map { getAllNumbers(it) }
            .flatMap { it.mapNotNull { findAnySequence(it) } }
        return hasSequence.sum()
    }

    check(part1(listOf("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")) == 1227775554L)
    check(part2(listOf("11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124")) == 4174379265L)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
