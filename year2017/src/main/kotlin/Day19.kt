object Day19 : Day<Any> {
    private val horizontal = listOf(Direction.LEFT, Direction.RIGHT)
    private val vertical = listOf(Direction.UP, Direction.DOWN)

    private fun String.walk() = lines().let { grid ->
        tailrec fun next(position: Point, direction: Point, path: List<Char>, steps: Int): Pair<String, Int> {
            fun grid(p: Point) = grid[p.y][p.x]
            val nextPosition = position + direction
            return when (val value = grid(nextPosition)) {
                ' ' -> path.joinToString("") to steps
                '+' -> {
                    val (first, second) = if (direction in horizontal) vertical else horizontal
                    val nextDirection = if (grid(nextPosition + first) != ' ') first else second
                    next(nextPosition, nextDirection, path, steps + 1)
                }

                else -> when {
                    value.isLetter() -> next(nextPosition, direction, path + value, steps + 1)
                    else -> next(nextPosition, direction, path, steps + 1)
                }
            }
        }

        val start = Point(grid.first().indexOf('|'), -1)
        next(start, Direction.DOWN, listOf(), 0)
    }

    override fun part1(input: String) = input.walk().first

    override fun part2(input: String) = input.walk().second
}