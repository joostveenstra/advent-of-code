package year2016

import framework.Context
import framework.Day
import util.match

class Day04(context: Context) : Day by context {
    data class Room(val name: String, val id: Int, val checksum: String)

    val rooms = lines.map { line ->
        line.match("""([\w\-]+)-(\d+)\[(\w{5})]""".toRegex())
            ?.let { (name, id, checksum) -> Room(name, id.toInt(), checksum) }
            ?: throw IllegalArgumentException("$line is not a valid room")
    }

    fun Room.isReal(): Boolean {
        val occurrences = name.filterNot { it == '-' }.groupBy { it }.toList()
        val sorted = occurrences.sortedWith(compareByDescending<Pair<Char, List<Char>>> { it.second.size }.thenBy { it.first })
        return checksum == sorted.take(5).map { it.first }.joinToString("")
    }

    fun Room.decrypt() = copy(name = name.map { if (it == '-') ' ' else 'a' + (it - 'a' + id) % 26 }.joinToString(""))

    fun part1() = rooms.filter { it.isReal() }.sumOf { it.id }
    fun part2() = rooms.map { it.decrypt() }.find { it.name == "northpole object storage" }?.id
}