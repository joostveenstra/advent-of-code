package year2016

import framework.Context
import framework.Day
import util.match

class Day21(context: Context) : Day by context {
    val swapPositions = "swap position (\\d+) with position (\\d+)".toRegex()
    val swapLetters = "swap letter (\\w) with letter (\\w)".toRegex()
    val rotateLeft = "rotate left (\\d+) steps?".toRegex()
    val rotateRight = "rotate right (\\d+) steps?".toRegex()
    val rotateByLetter = "rotate based on position of letter (\\w)".toRegex()
    val reverse = "reverse positions (\\d+) through (\\d+)".toRegex()
    val move = "move position (\\d+) to position (\\d+)".toRegex()

    fun String.swapPositions(x: Int, y: Int) = replaceRange(x, x + 1, "" + get(y)).replaceRange(y, y + 1, "" + get(x))
    fun String.swapLetters(a: String, b: String) = swapPositions(indexOf(a), indexOf(b))
    fun String.rotateLeft(steps: Int) = drop(steps) + take(steps)
    fun String.rotateRight(steps: Int) = takeLast(steps) + dropLast(steps)
    fun String.rotateRightByLetter(a: String) = rotateRight(indexOf(a).let { (it + if (it >= 4) 2 else 1) % length })
    fun String.rotateLeftByLetter(a: String) = rotateLeft(indexOf(a).let { if (it == 0) 1 else if (it % 2 == 0) it / 2 + 5 else it / 2 + 1 })
    fun String.reverse(x: Int, y: Int) = replaceRange(x..y, slice(x..y).reversed())
    fun String.move(x: Int, y: Int) = removeRange(x..x).let { removed ->
        removed.getOrNull(y)?.let { removed.replaceRange(y, y + 1, "" + get(x) + it) } ?: (removed + get(x))
    }

    fun part1() = lines.fold("abcdefgh") { password, op ->
        with(password) {
            op.match(swapPositions)?.let { (x, y) -> swapPositions(x.toInt(), y.toInt()) }
                ?: op.match(swapLetters)?.let { (a, b) -> swapLetters(a, b) }
                ?: op.match(rotateLeft)?.let { (steps) -> rotateLeft(steps.toInt()) }
                ?: op.match(rotateRight)?.let { (steps) -> rotateRight(steps.toInt()) }
                ?: op.match(rotateByLetter)?.let { (a) -> rotateRightByLetter(a) }
                ?: op.match(reverse)?.let { (x, y) -> reverse(x.toInt(), y.toInt()) }
                ?: op.match(move)?.let { (x, y) -> move(x.toInt(), y.toInt()) }
                ?: throw IllegalArgumentException("$op is not a valid operation")
        }
    }

    fun part2() = lines.foldRight("fbgdceah") { op, password ->
        with(password) {
            op.match(swapPositions)?.let { (x, y) -> swapPositions(x.toInt(), y.toInt()) }
                ?: op.match(swapLetters)?.let { (a, b) -> swapLetters(a, b) }
                ?: op.match(rotateLeft)?.let { (steps) -> rotateRight(steps.toInt()) }
                ?: op.match(rotateRight)?.let { (steps) -> rotateLeft(steps.toInt()) }
                ?: op.match(rotateByLetter)?.let { (a) -> rotateLeftByLetter(a) }
                ?: op.match(reverse)?.let { (x, y) -> reverse(x.toInt(), y.toInt()) }
                ?: op.match(move)?.let { (x, y) -> move(y.toInt(), x.toInt()) }
                ?: throw IllegalArgumentException("$op is not a valid operation")
        }
    }
}