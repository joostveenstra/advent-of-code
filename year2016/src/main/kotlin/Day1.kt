object Day1 : Day<Int> {
    private val start = Point(0, 0)
    private val initial = State(listOf(start), Direction.NORTH)

    data class State (val positions: List<Point>, val direction: Point)

    private fun String.toMoves() = split(", ").map { it.first() to it.drop(1).toInt() }

    private fun List<Pair<Char, Int>>.walk() = scan(initial) { (positions, direction), (turn, steps) ->
        val nextDirection = if (turn == 'L') direction.turnLeft() else direction.turnRight()
        val nextPositions = (1..steps).map { positions.last() + nextDirection * it }
        State(nextPositions, nextDirection)
    }.flatMap { it.positions }

    override fun part1(input: String) = input.toMoves().walk().last().manhattanDistanceTo(start)

    override fun part2(input: String) = input.toMoves().walk().groupBy { it }.filterValues { it.size > 1 }.keys.first().manhattanDistanceTo(start)
}