object Day14 : Day<Any> {
    data class Reindeer(val speed: Int, val fly: Int, val rest: Int) {
        fun distance(time: Int): Int {
            val total = fly + rest
            val complete = time / total
            val partial = minOf(time % total, fly)
            return speed * (complete * fly + partial)
        }
    }

    private fun String.toReindeer() = lines().map { line ->
        "\\d+".toRegex().findAll(line).map { it.value.toInt() }.toList().let { (speed, fly, rest) -> Reindeer(speed, fly, rest) }
    }

    private fun String.toSeconds() = if (lines().size == 2) 1000 else 2503

    override fun part1(input: String): Int {
        val seconds = input.toSeconds()
        return input.toReindeer().maxOf { it.distance(seconds) }
    }

    override fun part2(input: String): Int {
        val seconds = input.toSeconds()
        val reindeer = input.toReindeer()
        return (1..seconds)
            .fold(List(reindeer.size) { 0 }) { state, time ->
                val distances = reindeer.map { it.distance(time) }
                distances.map { if (it == distances.max()) 1 else 0 }.zip(state) { a, b -> a + b }
            }.max()
    }
}