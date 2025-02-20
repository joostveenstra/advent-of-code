package year2015

import framework.Context
import framework.Day
import util.allInts

class Day12(context: Context) : Day by context {
    sealed interface Json
    data class JsonNumber(val value: Int) : Json
    data class JsonString(val value: String) : Json
    data class JsonArray(val values: List<Json>) : Json
    data class JsonObject(val values: List<Json>) : Json

    val numbers = "(-?\\d+)(.*)".toRegex()
    val strings = "\"(\\w+)\"(.*)".toRegex()

    fun String.toJson(): Pair<Json, String> =
        numbers.matchEntire(this)?.destructured?.let { (number, rest) -> JsonNumber(number.toInt()) to rest }
            ?: strings.matchEntire(this)?.destructured?.let { (string, rest) -> JsonString(string) to rest }
            ?: when (first()) {
                '[' -> drop(1).toArray(JsonArray(listOf()))
                '{' -> drop(1).toObject(JsonObject(listOf()))
                else -> throw IllegalArgumentException("Invalid Json string: $this")
            }

    fun String.toArray(jArray: JsonArray): Pair<Json, String> {
        val (value, rest) = toJson()
        val next = JsonArray(jArray.values + value)
        return if (rest.startsWith(']')) next to rest.drop(1) else rest.drop(1).toArray(next)
    }

    fun String.toObject(jObject: JsonObject): Pair<Json, String> {
        val (_, rest1) = toJson()
        val (value, rest) = rest1.drop(1).toJson()
        val next = JsonObject(jObject.values + value)
        return if (rest.startsWith('}')) next to rest.drop(1) else rest.drop(1).toObject(next)
    }

    fun Json.evaluate(): Int = when (this) {
        is JsonNumber -> value
        is JsonString -> 0
        is JsonArray -> values.sumOf { it.evaluate() }
        is JsonObject -> if (JsonString("red") in values) 0 else values.sumOf { it.evaluate() }
    }

    fun part1() = input.allInts().sum()
    fun part2() = input.toJson().first.evaluate()
}