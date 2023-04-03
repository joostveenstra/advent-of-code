object Day10 : Day<Int> {
    private fun String.next(): String {
        val builder = StringBuilder()
        plus('x').fold(0 to first()) { (count, current), next ->
            if (current == next) {
                count + 1 to current
            } else {
                builder.append(count).append(current)
                1 to next
            }
        }
        return builder.toString()
    }

    private fun String.generate(turns: Int) = generateSequence(this) { it.next() }.drop(turns).first().length

    override fun part1(input: String) = input.generate(40)

    override fun part2(input: String) = input.generate(50)
}