object Day11 : Day<Int> {
    private val moves = listOf(
        Resources(2, 0),
        Resources(1, 0),
        Resources(1, 1),
        Resources(0, 1),
        Resources(0, 2)
    )

    private val adjacent = mapOf(
        0 to listOf(1),
        1 to listOf(0, 2),
        2 to listOf(1, 3),
        3 to listOf(2)
    )

    data class Resources(val chips: Int, val generators: Int) {
        operator fun plus(other: Resources) = Resources(chips + other.chips, generators + other.generators)
        operator fun minus(other: Resources) = Resources(chips - other.chips, generators - other.generators)
    }

    data class State(val floor: Int, val floors: List<Resources>)

    private fun String.toInitial(): State {
        val floors = lines().map { line ->
            val chips = "microchip".toRegex().findAll(line).count()
            val generators = "generator".toRegex().findAll(line).count()
            Resources(chips, generators)
        }
        return State(0, floors)
    }

    private fun State.isValid() = floors.all { it.chips >= 0 && it.generators >= 0 && (it.generators == 0 || it.chips <= it.generators) }

    private fun State.isEnd() = floor == 3 && floors.take(3).all { it.chips == 0 && it.generators == 0 }

    private fun State.next() = buildList {
        for (next in adjacent.getValue(floor)) for (move in moves)
            add(State(next, floors.mapIndexed { f, r -> if (f == floor) r - move else if (f == next) r + move else r }))
    }

    private fun minimize(initial: State): Int {
        val queue = dequeOf(initial)
        val visited = mutableMapOf(initial to 0)

        while (queue.isNotEmpty()) {
            val current = queue.removeFirst()
            val cost = visited.getValue(current) + 1
            current.next().filter { it.isValid() }.forEach { next ->
                if (next !in visited || cost < visited.getValue(next)) {
                    visited[next] = cost
                    queue.addLast(next)
                }
            }
        }

        return visited.entries.first { (k, _) -> k.isEnd() }.value
    }

    override fun part1(input: String) = minimize(input.toInitial())

    override fun part2(input: String): Int {
        val initial = input.toInitial()
        val expanded = initial.copy(floors = initial.floors.mapIndexed { f, r -> if (f == 0) r + Resources(2, 2) else r })
        return minimize(expanded)
    }
}