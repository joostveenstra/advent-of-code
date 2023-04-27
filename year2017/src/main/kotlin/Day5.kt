object Day5 : Day<Int> {
    private fun String.toInstructions() = lines().map { it.toInt() }.toMutableList()

    private tailrec fun MutableList<Int>.execute(index: Int, steps: Int, jump: (Int) -> Int): Int =
        if (index >= size) steps
        else {
            val offset = this[index]
            this[index] = jump(offset)
            execute(index + offset, steps + 1, jump)
        }

    override fun part1(input: String) = input.toInstructions().execute(0, 0) { offset -> offset + 1 }

    override fun part2(input: String) = input.toInstructions().execute(0, 0) { offset -> if (offset >= 3) offset - 1 else offset + 1 }
}