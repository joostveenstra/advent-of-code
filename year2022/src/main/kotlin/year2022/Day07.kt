package year2022

import framework.Context
import framework.Day
import util.dequeOf
import util.peek
import util.pop
import util.push

class Day07(context: Context) : Day by context {
    class Directory(val subDirs: MutableList<Directory> = mutableListOf(), var fileSizes: Int = 0) {
        val size: Int
            get() = fileSizes + subDirs.sumOf { it.size }
    }

    val dirSizes = buildList {
        val root = Directory().also { add(it) }
        val path = dequeOf(root)
        lines.drop(1).forEach { command ->
            when {
                command == "$ cd .." -> path.pop()
                command.first().isDigit() -> path.peek().fileSizes += command.substringBefore(' ').toInt()
                command.startsWith("$ cd") -> Directory().let {
                    path.peek().subDirs += it
                    path.push(it)
                    add(it)
                }
            }
        }
    }.map { it.size }

    fun part1() = dirSizes.filter { it <= 100_000 }.sum()
    fun part2(): Int {
        val spaceToFree = 30_000_000 - (70_000_000 - dirSizes.first())
        return dirSizes.filter { it >= spaceToFree }.min()
    }
}