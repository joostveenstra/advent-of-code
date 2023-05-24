import kotlinx.collections.immutable.*

object Day16 : Day<Int> {
    data class Valve(val flow: Int, val neighbours: List<String>)

    data class State(val todo: PersistentSet<String>, val visited: PersistentSet<String>, val from: String, val time: Int, val pressure: Int)

    private fun String.toValves(): Triple<Map<String, Valve>, Map<String, Map<String, Int>>, Set<String>> {
        val valves = lines().associate { line ->
            line.split("[^A-Z0-9]+".toRegex()).let {
                val (_, name, flowRate) = it
                name to Valve(flowRate.toInt(), it.drop(3))
            }
        }
        val paths = valves.mapValues { (k, _) -> valves.determineShortestPathsFrom(k) }
        val valvesToVisit = valves.filterValues { it.flow > 0 }.keys
        return Triple(valves, paths, valvesToVisit)
    }

    private fun Map<String, Valve>.determineShortestPathsFrom(root: String): Map<String, Int> {
        val queue = dequeOf(root)
        val visited = mutableMapOf(root to 1)

        while (queue.isNotEmpty()) {
            val valve = queue.removeFirst()
            getValue(valve).neighbours.filterNot { it in visited }.forEach { next ->
                queue.addLast(next)
                visited[next] = visited.getValue(valve) + 1
            }
        }

        return visited
    }

    private fun determineSubSetsMaxPressure(input: String, initialTime: Int): Map<Set<String>, Int> {
        val (valves, paths, valvesToVisit) = input.toValves()
        val initial = State(valvesToVisit.toPersistentSet(), persistentSetOf(), "AA", initialTime, 0)
        val stack = dequeOf(initial)
        val maxPressure = mutableMapOf<Set<String>, Int>().withDefault { 0 }

        while (stack.isNotEmpty()) {
            val (todo, visited, from, time, pressure) = stack.pop()
            maxPressure[visited] = maxOf(maxPressure.getValue(visited), pressure)
            for (next in todo) {
                val remaining = time - paths.getValue(from).getValue(next)
                if (remaining > 0) {
                    val extra = remaining * valves.getValue(next).flow
                    stack.push(State(todo - next, visited + next, next, remaining, pressure + extra))
                }
            }
        }

        return maxPressure
    }

    override fun part1(input: String) = determineSubSetsMaxPressure(input, 30).values.max()

    override fun part2(input: String): Int {
        val subSetsMax = determineSubSetsMaxPressure(input, 26)
        val disjoint = buildList {
            for ((you, youPressure) in subSetsMax)
                for ((elephant, elephantPressure) in subSetsMax)
                    if ((you intersect elephant).isEmpty())
                        add(youPressure + elephantPressure)
        }
        return disjoint.max()
    }
}