object Day4 : Day<Int?> {
    data class Room(val name: String, val id: Int, val checksum: String)

    private fun String.toRooms() = lines().map {
        """([\w\-]+)-(\d+)\[(\w{5})]""".toRegex().matchEntire(it)?.destructured
            ?.let { (name, id, checksum) -> Room(name, id.toInt(), checksum) } ?: throw IllegalArgumentException("This should never happen")
    }

    private fun Room.isReal(): Boolean {
        val occurrences = name.filterNot { it == '-' }.groupBy { it }.map { (k, v) -> -v.count() to k }
        val sorted = occurrences.sortedWith(compareBy<Pair<Int, Char>> { it.first }.thenBy { it.second })
        return checksum == sorted.take(5).map { it.second }.joinToString("")
    }

    private fun Room.decrypt() = copy (name = name.map { if (it == '-') ' ' else 'a' + (it - 'a' + id) % 26 }.joinToString(""))

    override fun part1(input: String) = input.toRooms().filter { it.isReal() }.sumOf { it.id }

    override fun part2(input: String) = input.toRooms().map { it.decrypt() }.find { it.name == "northpole object storage" }?.id
}