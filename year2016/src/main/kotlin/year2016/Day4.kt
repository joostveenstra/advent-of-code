package year2016

import framework.Day
import util.match

object Day4 : Day<Int?> {
    data class Room(val name: String, val id: Int, val checksum: String)

    private fun String.toRooms() = lines().map { line ->
        line.match("""([\w\-]+)-(\d+)\[(\w{5})]""".toRegex())
            ?.let { (name, id, checksum) -> Room(name, id.toInt(), checksum) } ?: throw IllegalArgumentException("$line is not a valid room")
    }

    private fun Room.isReal(): Boolean {
        val occurrences = name.filterNot { it == '-' }.groupBy { it }.toList()
        val sorted = occurrences.sortedWith(compareByDescending<Pair<Char, List<Char>>> { it.second.size }.thenBy { it.first })
        return checksum == sorted.take(5).map { it.first }.joinToString("")
    }

    private fun Room.decrypt() = copy(name = name.map { if (it == '-') ' ' else 'a' + (it - 'a' + id) % 26 }.joinToString(""))

    override fun part1(input: String) = input.toRooms().filter { it.isReal() }.sumOf { it.id }

    override fun part2(input: String) = input.toRooms().map { it.decrypt() }.find { it.name == "northpole object storage" }?.id
}