package year2023

import framework.Context
import framework.Day
import util.*
import kotlin.math.absoluteValue

/**
 * Uses https://en.wikipedia.org/wiki/Shoelace_formula to calculate the area enclosed by the loop
 * and https://en.wikipedia.org/wiki/Pick%27s_theorem to calculate the amount of points inside that area
 */
class Day10(context: Context) : Day by context {
    val grid = input.toCharGrid()
    val path = grid.walk()

    fun determinant(a: Point, b: Point) = a.x * b.y - a.y * b.x

    fun CharGrid.walk(): Pair<Int, Int> {
        tailrec fun step(position: Point, direction: Direction, area: Int, steps: Int): Pair<Int, Int> {
            val nextPosition = position + direction
            val determinant = determinant(position, nextPosition)
            val nextDirection = when (get(nextPosition)) {
                '|', '-' -> direction
                'L' -> if (direction == SOUTH) EAST else NORTH
                'J' -> if (direction == SOUTH) WEST else NORTH
                '7' -> if (direction == NORTH) WEST else SOUTH
                'F' -> if (direction == NORTH) EAST else SOUTH
                else -> return steps to (area + determinant).absoluteValue / 2
            }
            return step(nextPosition, nextDirection, area + determinant, steps + 1)
        }

        val start = findPoint('S')
        val direction = if (getOrNull(start + NORTH) in listOf('|', '7', 'F')) NORTH else SOUTH
        return step(start, direction, 0, 1)
    }

    fun part1() = path.let { (steps) -> steps / 2 }
    fun part2() = path.let { (steps, area) -> area - steps / 2 + 1 }
}