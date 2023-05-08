object Day7 : Day<Any> {
    data class Program(val name: String, val weight: Int, val total: Int, val unbalanced: Boolean, val children: List<Program>)

    private fun String.toTower(): Program {
        val regex = "[(), ]+".toRegex()
        val weights = lines().associate { line -> line.split(regex).let { (name, weight) -> name to weight.toInt() } }
        val edges = lines().associate { line -> line.split(regex).let { it.first() to it.drop(3) } }

        fun buildTower(name: String): Program {
            val weight = weights.getValue(name)
            val children = edges.getValue(name).map(::buildTower)
            val total = weight + children.sumOf { it.total }
            val unbalanced = children.any { it.total != children.first().total }
            return Program(name, weight, total, unbalanced, children)
        }

        val nonRoot = edges.values.flatten().toSet()
        val root = edges.keys.first { it !in nonRoot }
        return buildTower(root)
    }

    private fun Program.findUnbalancedWeight(): Int = children.find { it.unbalanced }
        ?.findUnbalancedWeight()
        ?: children.partition { it.total == children.first().total }.let { (left, right) ->
            val (l) = left
            val (r) = right
            if (left.size == 1) l.weight + r.total - l.total else r.weight + l.total - r.total
        }

    override fun part1(input: String) = input.toTower().name

    override fun part2(input: String) = input.toTower().findUnbalancedWeight()
}