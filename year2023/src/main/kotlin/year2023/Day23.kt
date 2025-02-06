package year2023

import framework.Day
import util.*

object Day23 : Day {
    private fun CharGrid.findLongestPath(start: Point, end: Point, neighbours: Point.() -> List<Pair<Point, Int>>): Int {
        val visited = mutableBooleanGrid(width, height)

        fun find(position: Point, distance: Int): Int =
            if (position == end) distance
            else {
                visited[position] = true
                val max = position.neighbours().filterNot { (p) -> visited[p] }.maxOfOrNull { (next, weight) ->
                    find(next, distance + weight)
                }
                visited[position] = false
                max ?: 0
            }

        return find(start, 0)
    }

    private fun CharGrid.findJunctions(start: Point, end: Point): Map<Point, List<Pair<Point, Int>>> {
        fun neighbours(point: Point) = point.cardinalNeighbours.filter { it in this && get(it) != '#' }

        val junctions = mutableMapOf(
            start to mutableListOf<Pair<Point, Int>>(),
            end to mutableListOf(),
        )

        entries.forEach { (point, char) ->
            if (char == '.' && neighbours(point).size > 2) {
                junctions[point] = mutableListOf()
            }
        }

        for (junction in junctions.keys) {
            val queue = dequeOf(junction to 0)
            val visited = mutableSetOf(junction)

            queue.drain { (point, distance) ->
                neighbours(point).filter { it !in visited }.forEach { next ->
                    if (next in junctions) {
                        junctions.getValue(junction) += (next to distance + 1)
                    } else {
                        queue += (next to distance + 1)
                        visited += next
                    }
                }
            }
        }

        val queue = dequeOf(start)
        val visited = mutableSetOf(start)

        queue.drain { point ->
            val neighbours = junctions.getValue(point)
            neighbours.removeAll { (p) -> p in visited }
            neighbours.filter { (next) -> junctions.getValue(next).size <= 3 }.forEach { (next) ->
                queue += next
                visited += next
            }
        }

        return junctions
    }

    override fun part1(input: String) = with(input.toCharGrid()) {
        val start = Point(0, 1)
        val end = Point(maxX - 1, maxY)

        fun neighbours(point: Point) = when (get(point)) {
            '>' -> listOf(point + RIGHT)
            '<' -> listOf(point + LEFT)
            '^' -> listOf(point + UP)
            'v' -> listOf(point + DOWN)
            else -> point.cardinalNeighbours.filter { it in this && get(it) != '#' }
        }.map { it to 1 }

        findLongestPath(start, end, ::neighbours)
    }

    override fun part2(input: String) = with(input.toCharGrid()) {
        val start = Point(0, 1)
        val end = Point(maxX - 1, maxY)
        val junctions = findJunctions(start, end)

        findLongestPath(start, end, junctions::getValue)
    }
}