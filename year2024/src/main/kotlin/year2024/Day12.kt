package year2024

import framework.Day
import util.*

object Day12 : Day {
    data class Region(val area: Int, val perimeter: Int, val sides: Int)

    private fun CharGrid.fillRegion(start: Point, seen: MutableBooleanGrid): Region {
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

    private fun CharGrid.findRegions(seen: MutableBooleanGrid = asMutableBooleanGrid()) =
        points.mapNotNull { start -> if (!seen[start]) fillRegion(start, seen) else null }

    override fun part1(input: String) = with(input.toCharGrid()) {
        findRegions().sumOf { it.area * it.perimeter }
    }

    override fun part2(input: String) = with(input.toCharGrid()) {
        findRegions().sumOf { it.area * it.sides }
    }
}