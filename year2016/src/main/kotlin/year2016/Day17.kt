package year2016

import framework.Context
import framework.Day
import util.*
import java.security.MessageDigest

class Day17(context: Context) : Day by context {
    val start = ORIGIN
    val goal = Point(3, 3)
    val directions = "UDLR".map { it to it.toDirection() }

    val md5: MessageDigest = MessageDigest.getInstance("MD5")

    fun String.toMd5(): String = md5.digest(toByteArray()).toHexString()

    fun Point.isValid() = (0..3).let { range -> x in range && y in range }

    fun pathsToVault(passcode: String): List<String> {
        val stack = dequeOf(start to "")
        val paths = mutableListOf<String>()

        stack.drain { (chamber, path) ->
            if (chamber == goal) paths.add(path)
            else directions.asSequence()
                .zip((passcode + path).toMd5().asSequence())
                .filter { (_, char) -> char > 'a' }
                .map { (direction, _) -> chamber + direction.second to path + direction.first }
                .filter { (position, _) -> position.isValid() }
                .forEach { next -> stack.push(next) }
        }

        return paths
    }

    val paths = pathsToVault(input)

    fun part1() = paths.minBy { it.length }
    fun part2() = paths.maxOf { it.length }
}