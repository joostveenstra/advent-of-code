typealias Elf = Point

object Day23 : Day<Int> {
    private val order = listOf(
        Direction.NORTH,
        Direction.SOUTH,
        Direction.WEST,
        Direction.EAST
    )

    data class State(val elves: Set<Elf>, val directions: List<Direction>, val stuck: Boolean = false)

    private fun String.toElves() = buildSet {
        lines().forEachIndexed { y, row ->
            row.forEachIndexed { x, col ->
                if (col == '#') add(Point(x, y))
            }
        }
    }

    private fun Elf.propose(elves: Set<Elf>, directions: List<Direction>): Elf? {
        val neighboursExist = neighbours.map { it in elves }
        val (e, s, w, n) = neighboursExist
        val (se, sw, nw, ne) = neighboursExist.subList(4, neighboursExist.size)
        val (north, south, west) = order

        return if (neighboursExist.any { it })
            directions.find {
                when (it) {
                    north -> !(n || nw || ne)
                    south -> !(s || sw || se)
                    west -> !(w || nw || sw)
                    else -> !(e || ne || se)
                }
            }?.let { this + it }
        else null
    }

    private fun round(state: State): State {
        val (elves, directions) = state
        val proposals = elves.associateWith { it.propose(elves, directions) }
        val occurrences = proposals.values.groupBy { it }.mapValues { (_, v) -> v.size }
        val next = proposals.map { (elf, proposal) ->
            proposal?.let { if (occurrences[it] == 1) it else elf } ?: elf
        }.toSet()

        return State(next, directions.drop(1) + directions.first(), elves == next)
    }

    private fun Set<Elf>.process(): Sequence<State> {
        val initial = State(this, order)
        return generateSequence(initial) { round(it) }
    }

    override fun part1(input: String): Int {
        val end = input.toElves().process().drop(10).first().elves

        val minX = end.minOf { it.x }
        val maxX = end.maxOf { it.x }
        val minY = end.minOf { it.y }
        val maxY = end.maxOf { it.y }

        val points = buildList { for (x in minX..maxX) for (y in minY..maxY) add(Point(x, y)) }
        return points.count { it !in end }
    }

    override fun part2(input: String): Int = input.toElves().process().indexOfFirst { it.stuck }
}