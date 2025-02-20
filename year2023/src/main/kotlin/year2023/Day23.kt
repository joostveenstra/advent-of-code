package year2023

import framework.Context
import framework.Day
import util.*

class Day23(context: Context) : Day by context {
    val grid = this@Day23.input.toCharGrid()
    val start = Point(0, 1)
    val end = Point(grid.maxX - 1, grid.maxY)

    fun longestPath(neighbours: Point.() -> List<Pair<Point, Int>>): Int {
        val visited = grid.asMutableBooleanGrid()

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

    fun CharGrid.neighbours(point: Point) = point.cardinalNeighbours.filter { it in this && get(it) != '#' }

    fun CharGrid.junctions(): Map<Point, List<Pair<Point, Int>>> {
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

    fun neighbours(point: Point) = when (grid[point]) {
        '>' -> listOf(point + RIGHT)
        '<' -> listOf(point + LEFT)
        '^' -> listOf(point + UP)
        'v' -> listOf(point + DOWN)
        else -> grid.neighbours(point)
    }.map { it to 1 }

    val junctions = grid.junctions()

    fun part1() = longestPath(::neighbours)
    fun part2() = longestPath(junctions::getValue)
}