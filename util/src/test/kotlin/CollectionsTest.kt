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

    @Test
    fun permutations() {
        val permutations = listOf(1, 2, 3).permutations().toList()
        assertEquals(listOf(listOf(1, 2, 3), listOf(1, 3, 2), listOf(2, 1, 3), listOf(2, 3, 1), listOf(3, 1, 2), listOf(3, 2, 1)), permutations)
    }

    @Test
    fun combinations() {
        val combinations2 = listOf(1, 2, 3, 4).combinations(2).toList()
        assertEquals(listOf(listOf(1, 2), listOf(1, 3), listOf(1, 4), listOf(2, 3), listOf(2, 4), listOf(3, 4)), combinations2)
        val combinations3 = listOf(1, 2, 3, 4).combinations(3).toList()
        assertEquals(listOf(listOf(1, 2, 3), listOf(1, 2, 4), listOf(1, 3, 4), listOf(2, 3, 4)), combinations3)
    }

    @Test
    fun transpose() {
        val list = listOf(listOf(1, 2, 3), listOf(4, 5, 6))
        val transposed = listOf(listOf(1, 4), listOf(2, 5), listOf(3, 6))
        assertEquals(transposed, list.transpose())
        assertEquals(list, transposed.transpose())
    }
}