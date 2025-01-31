package year2016

import framework.Day
import util.*
import java.security.MessageDigest
import java.util.*

object Day17 : Day {
    private val start = ORIGIN
    private val goal = Point(3, 3)

    private val directions = "UDLR".map { it to it.toDirection() }

    private val md5 = MessageDigest.getInstance("MD5")

    private fun String.toMd5() = HexFormat.of().formatHex(md5.digest(toByteArray()))

    private fun Point.isValid() = (0..3).let { range -> x in range && y in range }

    private fun pathsToVault(passcode: String): List<String> {
        val stack = dequeOf(start to "")
        val paths = mutableListOf<String>()

        stack.drain { (chamber, path) ->
            if (chamber == goal) paths += path
            else directions.asSequence()
                .zip((passcode + path).toMd5().asSequence())
                .filter { (_, char) -> char > 'a' }
                .map { (direction, _) -> chamber + direction.second to path + direction.first }
                .filter { (position, _) -> position.isValid() }
                .forEach { next -> stack.push(next) }
        }

        return paths
    }

    override fun part1(input: String) = pathsToVault(input).minBy { it.length }

    override fun part2(input: String) = pathsToVault(input).maxOf { it.length }
}