package util

class Grid<T>(
    val width: Int,
    val height: Int,
    private val elements: MutableList<T>
) {
    val xs: IntRange get() = (0..<width)
    val ys: IntRange get() = (0..<height)
    val rows: List<List<T>> get() = ys.map(::getRow)
    val columns: List<List<T>> get() = xs.map(::getColumn)

    val progressions by lazy {
        mapOf(
            NORTH to ys.flatMap { y -> xs.map { x -> Point(x, y) } },
            WEST to xs.flatMap { x -> ys.map { y -> Point(x, y) } },
            SOUTH to ys.reversed().flatMap { y -> xs.map { x -> Point(x, y) } },
            EAST to xs.reversed().flatMap { x -> ys.map { y -> Point(x, y) } }
        )
    }

    operator fun get(p: Point): T = elements[p.y * width + p.x]

    operator fun set(p: Point, value: T) {
        elements[p.y * width + p.x] = value
    }

    operator fun contains(p: Point): Boolean = p.x >= 0 && p.x < width && p.y >= 0 && p.y < height

    fun safeGet(p: Point): T? = if (p in this) get(p) else null

    fun getRow(y: Int): List<T> = (y * width).let { offset -> elements.slice(offset..<offset + width) }

    fun getColumn(x: Int): List<T> = ys.map { y -> elements[y * width + x] }

    private fun translate(index: Int): Point {
        val x = index % width
        val y = index / width
        return Point(x, y)
    }

    fun find(value: T): Point = elements.indexOf(value).let { index ->
        if (index >= 0) translate(index)
        else throw NoSuchElementException("Item not found in grid.")
    }

    fun findAll(value: T): List<Point> = elements.indices.filter { elements[it] == value }.map(::translate)

    fun swap(a: Point, b: Point) {
        val tmp = this[a]
        this[a] = this[b]
        this[b] = tmp
    }

    companion object {
        fun <T> of(input: String, transform: (Char) -> T): Grid<T> {
            val raw = input.lines()
            val width = raw[0].length
            val height = raw.size
            val elements = raw.flatMap { it.map(transform) }.toMutableList()
            return Grid(width, height, elements)
        }
    }
}

fun String.toCharGrid() = Grid.of(this) { it }
fun String.toIntGrid() = Grid.of(this) { it.digitToInt() }