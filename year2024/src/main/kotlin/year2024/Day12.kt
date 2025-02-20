package year2024

import framework.Context
import framework.Day
import util.*

class Day12(context: Context) : Day by context {
    val grid = input.toCharGrid()
    val seen = grid.asMutableBooleanGrid()

    data class Region(val area: Int, val perimeter: Int, val sides: Int)

    fun CharGrid.fillRegion(start: Point): Region {
        val region = object {
            val type = get(start)
            operator fun contains(p: Point) = getOrNull(p) == type
        }
        val queue = dequeOf(start)
        var area = 0
        var perimeter = 0
        var edge = 0

        seen.enable(start)

        queue.drain { point ->
            area += 1
            cardinal.forEach { direction ->
                val next = point + direction
                if (next in region) {
                    if (!seen[next]) {
                        seen.enable(next)
                        queue += next
                    }
                } else {
                    perimeter += 1
                    edge += direction.orthogonal().sumOf { o ->
                        (point + o !in region || point + o + direction in region).toInt()
                    }
                }
            }
        }

        return Region(area, perimeter, edge / 2)
    }

    fun CharGrid.findRegions() = points.mapNotNull { start -> if (!seen[start]) fillRegion(start) else null }

    val regions = grid.findRegions()

    fun part1() = regions.sumOf { it.area * it.perimeter }
    fun part2() = regions.sumOf { it.area * it.sides }
}