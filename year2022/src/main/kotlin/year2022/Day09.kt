package year2022
import framework.Context
import framework.Day
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.plus
import util.ORIGIN
import util.Point
import util.plus
import util.toDirection
import kotlin.math.absoluteValue
import kotlin.math.sign

class Day09(context: Context) : Day by context {
    val moves = lines.flatMap {
        it.split(' ').let { (direction, steps) ->
            List(steps.toInt()) { direction.first().toDirection() }
        }
    }

    infix fun Point.touches(other: Point) = (x - other.x).absoluteValue <= 1 && (y - other.y).absoluteValue <= 1
    infix fun Point.moveTowards(other: Point) = Point(x + (other.x - x).sign, y + (other.y - y).sign)

    fun simulate(size: Int): Int {
        val start = ORIGIN
        val initial = List(size) { start } to persistentListOf(start)
        val (_, visited) = moves.fold(initial) { (rope, visited), move ->
            val nextHead = rope.first() + move
            val nextRope = rope.drop(1).scan(nextHead) { head, tail ->
                if (tail touches head) tail else tail moveTowards head
            }
            nextRope to visited + nextRope.last()
        }
        return visited.toSet().size
    }

    fun part1() = simulate(2)
    fun part2() = simulate(10)
}