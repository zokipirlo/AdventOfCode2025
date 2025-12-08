import utils.println
import utils.readInput
import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        val rotations = input.map {
            it
                .replace("R", "+")
                .replace("L", "-")
                .toInt()
        }
        var current = 50
        var zeros = 0
        rotations.forEach { rotation ->
            current = (current + rotation).mod(100)
            if (current == 0) {
                zeros++
            }
        }
        return zeros
    }

    fun part2(input: List<String>): Int {
        val rotations = input.map {
            it
                .replace("R", "+")
                .replace("L", "-")
                .toInt()
        }
        var current = 50
        var zeros = 0
        rotations.forEach { rotation ->
            val addZeros = rotation / 100
            zeros += abs(addZeros)
            val remainingRotation = rotation % 100
            val newValue = (current + remainingRotation).mod(100)
            when {
                newValue == 0 -> zeros++
                current != 0 && newValue > current && rotation < 0 -> zeros++
                current != 0 && current > newValue && rotation > 0 -> zeros++
            }
            current = newValue
        }
        return zeros
    }

    // Test if implementation meets criteria from the description, like:
//    check(part1(listOf("test_input")) == 1)

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
