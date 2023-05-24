package util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

class RangesTest {

    @Test
    fun contains() {
        val range = 1..10
        assert(1..5 in range)
        assert(0..1 !in range)
        assert(10..11 !in range)
    }

    @Test
    fun overlaps() {
        val range = 1..10
        assert(0..1 overlaps range)
        assert(5..11 overlaps range)
        assert(4..7 overlaps range)
        assertFalse(11..15 overlaps range)
    }

    @Test
    fun merge() {
        val ranges = listOf(1..10, 8..16, 20..24)
        assertEquals(listOf(1..16, 20..24), ranges.merge())
    }
}