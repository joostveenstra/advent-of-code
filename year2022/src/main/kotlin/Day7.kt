object Day7 : Day<Int> {
    class Directory(val subDirs: MutableList<Directory> = mutableListOf(), var fileSizes: Int = 0) {
        val size: Int
            get() = fileSizes + subDirs.sumOf { it.size }
    }

    private fun String.toDirSizes() = buildList {
        val root = Directory().also { add(it) }
        val path = dequeOf(root)
        lines().drop(1).forEach { command ->
            when {
                command == "$ cd .." -> path.pop()
                command.first().isDigit() -> path.peek().fileSizes += command.substringBefore(' ').toInt()
                command.startsWith("$ cd") -> Directory().let {
                    path.peek().subDirs += it
                    path.push(it)
                    add(it)
                }
            }
        }
    }.map { it.size }

    override fun part1(input: String) = input.toDirSizes().filter { it <= 100_000 }.sum()

    override fun part2(input: String): Int {
        val dirSizes = input.toDirSizes()
        val spaceToFree = 30_000_000 - (70_000_000 - dirSizes.first())
        return dirSizes.filter { it >= spaceToFree }.min()
    }
}