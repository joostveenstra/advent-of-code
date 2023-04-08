object Day8 : Day<Any> {
    sealed interface Operation
    data class Rect(val width: Int, val height: Int) : Operation
    data class Row(val row: Int, val offset: Int) : Operation
    data class Col(val col: Int, val offset: Int) : Operation

    data class Screen(val width: Int, val height: Int)

    private fun String.toScreen() = if (lines().size == 4) Screen(7, 3) else Screen(50, 6)

    private fun String.toOperation(): Operation {
        val (a, b) = allInts().toList()
        return when {
            "rect" in this -> Rect(a, b)
            "row" in this -> Row(a, b)
            else -> Col(a, b)
        }
    }

    private fun Screen.draw(input: String): Set<Point> = input.lines().map { it.toOperation() }.fold(emptySet()) { points, op ->
        when (op) {
            is Rect -> points + (0 until op.width).flatMap { x -> (0 until op.height).map { y -> Point(x, y) } }
            is Row -> points.map { p -> if (op.row != p.y) p else Point((p.x + op.offset) % width, p.y) }.toSet()
            is Col -> points.map { p -> if (op.col != p.x) p else Point(p.x, (p.y + op.offset) % height) }.toSet()
        }
    }

    override fun part1(input: String): Int {
        val screen = input.toScreen()
        return screen.draw(input).size
    }

    override fun part2(input: String): String {
        val screen = input.toScreen()
        val points = screen.draw(input)
        return (0 until screen.height).joinToString("\n") { y ->
            (0 until screen.width).map { x ->
                if (Point(x, y) in points) '#' else '.'
            }.joinToString("")
        }
    }
}