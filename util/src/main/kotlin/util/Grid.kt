package util

fun Pair<Int, Int>.toPoint() = Point(first, second)
fun Point.toIndex(width: Int) = x + y * width
fun toPointIndex(x: Int, y: Int, width: Int) = x + y * width
fun pointFromIndex(index: Int, width: Int) = Point(index % width, index / width)

interface Plane {
    val width: Int
    val height: Int

    fun column(index: Int): List<Point> {
        require(index in xRange) { "column out of bounds: $index in width $width" }
        return yRange.map { Point(index, it) }
    }

    fun row(index: Int): List<Point> {
        require(index in yRange) { "row out of bounds: $index in height $height" }
        return xRange.map { Point(it, index) }
    }

    fun takeOrNull(p: Point) = p.takeIf { it in this }
    fun Point.cardinal() = dCardinal.mapNotNull { takeOrNull(this + it) }
    fun Point.diagonal() = dDiagonal.mapNotNull { takeOrNull(this + it) }
    fun Point.allAdjacent() = allDirections.mapNotNull { takeOrNull(this + it) }

    fun Point.toIndex() = x + y * width
    fun toPointIndex(x: Int, y: Int) = x + y * width
    fun pointFromIndex(index: Int) = Point(index % width, index / width)
}

val Plane.minX get() = 0
val Plane.minY get() = 0
val Plane.maxX get() = width - 1
val Plane.maxY get() = height - 1
val Plane.xRange: IntRange get() = 0..<width
val Plane.yRange: IntRange get() = 0..<height

val Plane.rows get() = yRange.map { y -> xRange.map { x -> Point(x, y) } }
val Plane.columns get() = xRange.map { x -> yRange.map { y -> Point(x, y) } }
val Plane.points get() = xRange.flatMap { x -> yRange.map { y -> Point(x, y) } }
val Plane.pointsSequence get() = xRange.asSequence().flatMap { x -> yRange.map { y -> Point(x, y) } }
val Plane.pointsFlipped get() = yRange.flatMap { y -> xRange.map { x -> Point(x, y) } }
val Plane.pointsFlippedSequence get() = yRange.asSequence().flatMap { y -> xRange.map { x -> Point(x, y) } }

operator fun Plane.contains(p: Point) = p.x in 0..<width && p.y in 0..<height

interface GridLike<T> : Plane, Iterable<T> {
    val elements: List<T>

    fun Point.cardinalElements() = dCardinal.mapNotNull { getOrNull(this + it) }
    fun Point.cardinalElementsIndexed() = dCardinal.mapNotNull { d -> (this + d).let { p -> getOrNull(p)?.let { p to it } } }
    fun Point.diagonalElements() = dDiagonal.mapNotNull { getOrNull(this + it) }
    fun Point.diagonalElementsIndexed() = dDiagonal.mapNotNull { d -> (this + d).let { p -> getOrNull(p)?.let { p to it } } }
    fun Point.allAdjacentElements() = allDirections.mapNotNull { getOrNull(this + it) }
    fun Point.allAdjacentElementsIndexed() = allDirections.mapNotNull { d -> (this + d).let { p -> getOrNull(p)?.let { p to it } } }
}

val <T> GridLike<T>.entries get() = points.map { it to get(it) }

fun <T> GridLike<T>.rowValues(y: Int) = (y * width).let { offset -> elements.slice(offset..<offset + width) }
fun <T> GridLike<T>.columnValues(x: Int) = yRange.map { y -> elements[toPointIndex(x, y)] }

val <T> GridLike<T>.rowsValues get() = yRange.map { rowValues(it) }
val <T> GridLike<T>.columnsValues get() = xRange.map { columnValues(it) }

operator fun <T> GridLike<T>.get(p: Point): T = getOrNull(p) ?: throw NoSuchElementException("Key $p is missing in the grid.")
fun <T> GridLike<T>.getOrNull(p: Point): T? = if (p in this) elements[p.toIndex()] else null

fun <T> GridLike<T>.findPoint(value: T): Point = elements.indexOf(value).let { index ->
    if (index >= 0) pointFromIndex(index)
    else throw NoSuchElementException("Element not found in grid.")
}

fun <T> GridLike<T>.findPoints(value: T): List<Point> = findPointsBy { it == value }

inline fun <T> GridLike<T>.findPointsBy(predicate: (T) -> Boolean) = elements.indices.filter { predicate(elements[it]) }.map { pointFromIndex(it) }

typealias BooleanGrid = GridLike<Boolean>
typealias MutableBooleanGrid = MutableGrid<Boolean>
typealias IntGrid = GridLike<Int>
typealias MutableIntGrid = MutableGrid<Int>
typealias CharGrid = GridLike<Char>
typealias MutableCharGrid = MutableGrid<Char>

data class Grid<T>(
    override val width: Int,
    override val height: Int,
    override val elements: List<T>
) : List<T> by elements, GridLike<T>

data class MutableGrid<T>(
    override val width: Int,
    override val height: Int,
    override val elements: MutableList<T>
) : MutableList<T> by elements, GridLike<T> {
    operator fun set(p: Point, value: T) {
        this[p.toIndex()] = value
    }
}

inline fun <T> List<String>.toGrid(transform: (Char) -> T) = Grid(
    width = first().length,
    height = size,
    elements = flatMap { it.map(transform) }
)

inline fun <T> List<String>.toMutableGrid(transform: (Char) -> T) = toGrid(transform).toMutableGrid()

fun <T> GridLike<T>.toGrid() = Grid(width, height, elements.toList())
fun <T> GridLike<T>.toMutableGrid() = MutableGrid(width, height, elements.toMutableList())

inline fun <T, R> GridLike<T>.asGrid(init: (Point) -> R) = grid(width, height, init)
inline fun <T, R> GridLike<T>.asMutableGrid(init: (Point) -> R) = mutableGrid(width, height, init)
fun <T> GridLike<T>.asIntGrid() = intGrid(width, height)
fun <T> GridLike<T>.asMutableIntGrid() = mutableIntGrid(width, height)
fun <T> GridLike<T>.asBooleanGrid() = booleanGrid(width, height)
fun <T> GridLike<T>.asMutableBooleanGrid() = mutableBooleanGrid(width, height)

fun String.toCharGrid(): CharGrid = lines().toCharGrid()
fun String.toDigitGrid(): IntGrid = lines().toDigitGrid()
fun List<String>.toCharGrid(): CharGrid = toGrid { it }
fun List<String>.toDigitGrid(): IntGrid = toGrid { it.digitToInt() }

fun intGrid(width: Int, height: Int): IntGrid = Grid(width, height, IntArray(width * height).toList())
fun mutableIntGrid(width: Int, height: Int) = MutableGrid(width, height, IntArray(width * height).toMutableList())
fun booleanGrid(width: Int, height: Int): BooleanGrid = Grid(width, height, BooleanArray(width * height).toList())
fun mutableBooleanGrid(width: Int, height: Int) = MutableGrid(width, height, BooleanArray(width * height).toMutableList())

inline fun <T> buildGrid(
    width: Int,
    height: Int,
    default: (Point) -> T,
    block: MutableGrid<T>.() -> Unit
): Grid<T> {
    val tempGrid = mutableGrid(width, height, default)
    tempGrid.block()
    return tempGrid.toGrid()
}

inline fun <T> buildGridDefault(width: Int, height: Int, default: T, block: MutableGrid<T>.() -> Unit) =
    buildGrid(width, height, { default }, block)

inline fun buildBooleanGrid(
    width: Int,
    height: Int,
    default: Boolean = false,
    block: MutableBooleanGrid.() -> Unit
) = buildGrid(width, height, { default }, block)

inline fun buildIntGrid(
    width: Int,
    height: Int,
    default: Int = 0,
    block: MutableIntGrid.() -> Unit
) = buildGrid(width, height, { default }, block)

inline fun <T> grid(width: Int, height: Int, init: (Point) -> T) =
    Grid(width, height, (0..<width * height).map { init(pointFromIndex(it, width)) })

inline fun <T> mutableGrid(width: Int, height: Int, init: (Point) -> T) =
    MutableGrid(width, height, (0..<width * height).map { init(pointFromIndex(it, width)) }.toMutableList())

fun MutableBooleanGrid.toggle(point: Point) {
    this[point] = !this[point]
}

fun MutableBooleanGrid.enable(point: Point) {
    this[point] = true
}

fun MutableBooleanGrid.disable(point: Point) {
    this[point] = false
}
