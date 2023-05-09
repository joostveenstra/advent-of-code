object Day12 : Day<Int> {
    private val numbers = "(-?\\d+)(.*)".toRegex()
    private val strings = "\"(\\w+)\"(.*)".toRegex()

    sealed interface Json
    data class JsonNumber(val value: Int) : Json
    data class JsonString(val value: String) : Json
    data class JsonArray(val values: List<Json>) : Json
    data class JsonObject(val values: List<Json>) : Json

    private fun String.toJson(): Pair<Json, String> =
        numbers.matchEntire(this)?.destructured?.let { (number, rest) -> JsonNumber(number.toInt()) to rest }
            ?: strings.matchEntire(this)?.destructured?.let { (string, rest) -> JsonString(string) to rest }
            ?: when (first()) {
                '[' -> drop(1).toArray(JsonArray(listOf()))
                '{' -> drop(1).toObject(JsonObject(listOf()))
                else -> throw IllegalArgumentException("Invalid Json string: $this")
            }

    private fun String.toArray(jArray: JsonArray): Pair<Json, String> {
        val (value, rest) = toJson()
        val next = JsonArray(jArray.values + value)
        return if (rest.startsWith(']')) next to rest.drop(1) else rest.drop(1).toArray(next)
    }

    private fun String.toObject(jObject: JsonObject): Pair<Json, String> {
        val (_, rest1) = toJson()
        val (value, rest) = rest1.drop(1).toJson()
        val next = JsonObject(jObject.values + value)
        return if (rest.startsWith('}')) next to rest.drop(1) else rest.drop(1).toObject(next)
    }

    private fun Json.evaluate(): Int = when (this) {
        is JsonNumber -> value
        is JsonString -> 0
        is JsonArray -> values.sumOf { it.evaluate() }
        is JsonObject -> if (JsonString("red") in values) 0 else values.sumOf { it.evaluate() }
    }

    override fun part1(input: String) = input.allInts().sum()

    override fun part2(input: String) = input.toJson().first.evaluate()
}