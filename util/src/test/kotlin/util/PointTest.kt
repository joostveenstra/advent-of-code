package util

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class PointTest {
    private val point = "1,2".toPoint()

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
            point.allNeighbours
        )
    }

    @Test
    fun manhattan() {
        assertEquals(6, point manhattan Point(4, 5))
    }

    @Test
    fun turnLeft() {
        assertEquals(RIGHT, DOWN.turnLeft())
    }

    @Test
    fun turnRight() {
        assertEquals(LEFT, DOWN.turnRight())
    }
}