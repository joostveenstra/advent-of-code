import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Point3DTest {
    val point = Point3D(1, 2, 3)

    @Test
    fun plus() {
        assertEquals(Point3D(3, 5, 7), point + Point3D(2, 3, 4))
    }

    @Test
    fun getCardinalNeighbours() {
        assertEquals(listOf(Point3D(2, 2, 3), Point3D(0, 2, 3), Point3D(1, 3, 3), Point3D(1, 1, 3), Point3D(1, 2, 4), Point3D(1, 2, 2)), point.cardinalNeighbours)
    }
}