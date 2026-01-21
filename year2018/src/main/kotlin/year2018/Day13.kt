package year2018

import framework.Context
import framework.Day
import util.*

class Day13(context: Context) : Day by context {
    data class Cart(val position: Point, val direction: Direction, val turns: Int = 0, val active: Boolean = true) : Comparable<Cart> {
        override fun compareTo(other: Cart) = compareValuesBy(this, other, { it.position.y }, { it.position.x })
    }

    val grid = input.toCharGrid()
    val initial = grid.points.mapNotNull { p -> grid[p].toDirectionOrNull()?.let { Cart(p, it) } }

    fun Cart.step(): Cart {
        val next = position + direction
        return copy(
            position = next,
            direction = when (grid[next]) {
                '/' -> if (direction.isVertical) direction.turnRight() else direction.turnLeft()
                '\\' -> if (direction.isHorizontal) direction.turnRight() else direction.turnLeft()
                '+' -> when (turns) {
                    0 -> direction.turnLeft()
                    1 -> direction
                    else -> direction.turnRight()
                }

                else -> direction
            },
            turns = if (grid[next] == '+') (turns + 1) % 3 else turns
        )
    }

    fun List<Cart>.collisions() = sequence {
        val carts = toMutableList()

        while (carts.size > 1) {
            carts.sort()
            carts.forEachIndexed { i, cart ->
                if (cart.active) {
                    val next = cart.step()
                    carts.withIndex()
                        .find { (_, other) -> other.active && other.position == next.position }
                        ?.let { (j, other) ->
                            yield(next.position)
                            carts[i] = next.copy(active = false)
                            carts[j] = other.copy(active = false)
                        }
                        ?: run { carts[i] = next }
                }
            }
            carts.retainAll { it.active }
        }

        yield(carts.first().position)
    }

    fun Point.format() = "$x,$y"

    fun part1() = initial.collisions().first().format()
    fun part2() = initial.collisions().last().format()
}