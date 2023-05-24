package year2015

import framework.Day

object Day7 : Day<UShort> {
    sealed interface Gate
    data class Wire(val op: String) : Gate
    data class Not(val op: String) : Gate
    data class And(val left: String, val right: String) : Gate
    data class Or(val left: String, val right: String) : Gate
    data class LeftShift(val op: String, val amount: Int) : Gate
    data class RightShift(val op: String, val amount: Int) : Gate

    private fun String.toGates() = lines().associate { line -> line.split(' ').let { it.last() to it.toGate() } }

    private fun List<String>.toGate() = when (size) {
        3 -> let { (op) -> Wire(op) }
        4 -> let { (_, op) -> Not(op) }
        else -> let { (left, op, right) ->
            when (op) {
                "AND" -> And(left, right)
                "OR" -> Or(left, right)
                "LSHIFT" -> LeftShift(left, right.toInt())
                else -> RightShift(left, right.toInt())
            }
        }
    }

    private fun String.evaluate(): UShort {
        val gates = toGates()
        val values = mutableMapOf<String, UShort>()

        fun evaluate(key: String): UShort = values.getOrPut(key) {
            key.toUShortOrNull() ?: with(gates.getValue(key)) {
                when (this) {
                    is Wire -> evaluate(op)
                    is Not -> evaluate(op).inv()
                    is And -> evaluate(left) and evaluate(right)
                    is Or -> evaluate(left) or evaluate(right)
                    is LeftShift -> (evaluate(op).toInt() shl amount).toUShort()
                    is RightShift -> (evaluate(op).toInt() shr amount).toUShort()
                }
            }
        }

        val target = if (gates.size <= 10) "d" else "a"
        return evaluate(target)
    }

    override fun part1(input: String) = input.evaluate()

    override fun part2(input: String) = "$input\n956 -> b".evaluate()
}