object Day3 : Day<Int> {
    private val directions = mapOf(
        '^' to Direction.NORTH,
        'v' to Direction.SOUTH,
        '>' to Direction.EAST,
        '<' to Direction.WEST
    )

    private fun Iterable<Char>.deliver() = map { directions.getValue(it) }.scan(Point(0, 0)) { position, move -> position + move }

    override fun part1(input: String) = input.asIterable().deliver().distinct().size

    override fun part2(input: String): Int {
        val (santa, robot) = input.withIndex().partition { it.index % 2 == 0 }.let { (left, right) -> left.map { it.value } to right.map { it.value } }
        return (santa.deliver() + robot.deliver()).distinct().size
    }
}