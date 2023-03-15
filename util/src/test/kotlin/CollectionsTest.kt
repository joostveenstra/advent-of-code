import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CollectionsTest {

    @Test
    fun product() {
        val ints = listOf(1, 2, 3, 4)
        assertEquals(24, ints.product())

        val longs = listOf(1L, 2, 3, 4)
        assertEquals(24L, longs.product())
    }

    @Test
    fun productOf() {
        val strings = listOf("a", "ab", "abc", "abcd")
        assertEquals(24, strings.productOf { it.length })
        assertEquals(24L, strings.productOf { it.length.toLong() })
    }

    @Test
    fun zipWithIndex() {
        val chars = listOf('a', 'b', 'c', 'd').zipWithIndex()
        assertEquals(listOf('a' to 0, 'b' to 1, 'c' to 2, 'd' to 3), chars)
    }
}