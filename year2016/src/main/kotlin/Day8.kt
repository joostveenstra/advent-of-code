object Day8 : Day<Any> {
    sealed interface Operation {
        companion object {
            fun of(input: String): Operation {
                val rect = "rect (\\d+)x(\\d+)".toRegex()
                val row = "rotate row y=(\\d+) by (\\d+)".toRegex()
                val col = "rotate column x=(\\d+) by (\\d+)".toRegex()
                return rect.matchEntire(input)?.destructured?.let { (a, b) -> Rect(a.toInt(), b.toInt()) }
                    ?: row.matchEntire(input)?.destructured?.let { (a, b) -> Row(a.toInt(), b.toInt()) }
                    ?: col.matchEntire(input)?.destructured?.let { (a, b) -> Col(a.toInt(), b.toInt()) }
                    ?: throw IllegalArgumentException("Invalid operation: $input")
            }
        }
    }

    data class Rect(val width: Int, val height: Int) : Operation
    data class Row(val row: Int, val offset: Int) : Operation
    data class Col(val col: Int, val offset: Int) : Operation

    data class Screen(val width: Int, val height: Int)

    private fun String.toScreen() = if (lines().size == 4) Screen(7, 3) else Screen(50, 6)

    private fun String.draw(screen: Screen): Set<Point> = lines().map(Operation::of).fold(emptySet()) { points, op ->
        when (op) {
            is Rect -> points + (0 until op.width).flatMap { x -> (0 until op.height).map { y -> Point(x, y) } }
            is Row -> points.map { p -> if (op.row != p.y) p else Point((p.x + op.offset) % screen.width, p.y) }.toSet()
            is Col -> points.map { p -> if (op.col != p.x) p else Point(p.x, (p.y + op.offset) % screen.height) }.toSet()
        }
    }

    override fun part1(input: String): Int {
        val screen = input.toScreen()
        return input.draw(screen).size
    }

    override fun part2(input: String): String {
        val screen = input.toScreen()
        val points = input.draw(screen)
        return (0 until screen.height).joinToString("\n") { y ->
            (0 until screen.width).map { x ->
                if (Point(x, y) in points) '#' else '.'
            }.joinToString("")
        }
    }
}