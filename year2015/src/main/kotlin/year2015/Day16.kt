package year2015

import framework.Context
import framework.Day

class Day16(context: Context) : Day by context {
    data class Aunt(val number: Int, val properties: Map<String, Int>)

    val footprint = mapOf(
        "children" to 3,
        "cats" to 7,
        "samoyeds" to 2,
        "pomeranians" to 3,
        "akitas" to 0,
        "vizslas" to 0,
        "goldfish" to 5,
        "trees" to 3,
        "cars" to 2,
        "perfumes" to 1
    )

    val aunts = lines.mapIndexed { index, line ->
        Aunt(index + 1, line.split("[: ,]+".toRegex()).drop(2).chunked(2).associate { (k, v) -> k to v.toInt() })
    }

    fun List<Aunt>.findNumberBy(predicate: (Map.Entry<String, Int>) -> Boolean) = first { it.properties.all(predicate) }.number

    fun part1() = aunts.findNumberBy { (k, v) -> footprint[k] == v }
    fun part2() = aunts.findNumberBy { (k, v) ->
        val value = footprint.getValue(k)
        when (k) {
            "cats", "trees" -> v > value
            "pomeranians", "goldfish" -> v < value
            else -> v == value
        }
    }
}