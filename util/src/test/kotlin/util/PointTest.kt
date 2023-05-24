package util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PointTest {
    private val point = Point.of("1,2")

    @Test
    fun plus() {
        assertEquals(Point(3, 5), point + Point(2, 3))
    }

    @Test
    fun cardinalNeighbours() {
        assertEquals(listOf(Point(2, 2), Point(1, 3), Point(0, 2), Point(1, 1)), point.cardinalNeighbours)
    }

    @Test
    fun diagonalNeighbours() {
        assertEquals(listOf(Point(2, 3), Point(0, 3), Point(0, 1), Point(2, 1)), point.diagonalNeighbours)
    }

    @Test
    fun neighbours() {
        assertEquals(
            listOf(
                Point(2, 2), Point(1, 3), Point(0, 2), Point(1, 1),
                Point(2, 3), Point(0, 3), Point(0, 1), Point(2, 1)
            ),
            point.neighbours
        )
    }

    @Test
    fun manhattanDistanceTo() {
        assertEquals(6, point.manhattanDistanceTo(Point(4, 5)))
    }

    @Test
    fun turnLeft() {
        assertEquals(Point(1, 0), Point(0, 1).turnLeft())
    }

    @Test
    fun turnRight() {
        assertEquals(Point(-1, 0), Point(0, 1).turnRight())
    }
}