object Day5 : Day<Int> {
    override fun part1(input: String): Int {
        val vowels = "[aeiou].*[aeiou].*[aeiou]".toRegex()
        val pair = "(.)\\1".toRegex()
        val naughty = "ab|cd|pq|xy".toRegex()
        return input.lines().count { vowels.containsMatchIn(it) && pair.containsMatchIn(it) && !naughty.containsMatchIn(it) }
    }

    override fun part2(input: String): Int {
        val pairs = "(..).*\\1".toRegex()
        val split = "(.).\\1".toRegex()
        return input.lines().count { pairs.containsMatchIn(it) && split.containsMatchIn(it) }
    }
}