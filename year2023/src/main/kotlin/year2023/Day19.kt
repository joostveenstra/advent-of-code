package year2023

import framework.Context
import framework.Day
import util.EMPTY_LINE

class Day19(context: Context) : Day by context {
    sealed interface Rule {
        val next: String
    }

    sealed interface Condition : Rule {
        val category: Char
        val value: Int
    }

    data class Send(override val next: String) : Rule
    data class Less(override val category: Char, override val value: Int, override val next: String) : Condition
    data class Greater(override val category: Char, override val value: Int, override val next: String) : Condition

    val workflows = this@Day19.input.split(EMPTY_LINE)[0].lines().associate { line ->
        val tokens = line.split('{', ':', ',', '}')
        val key = tokens.first()
        val rules = tokens.drop(1).chunked(2).map { (left, right) ->
            if (right.isEmpty()) {
                Send(left)
            } else {
                val category = left[0]
                val value = left.substring(2).toInt()
                when (left[1]) {
                    '<' -> Less(category, value, right)
                    else -> Greater(category, value, right)
                }
            }
        }
        key to rules
    }
    val parts = this@Day19.input.split(EMPTY_LINE)[1].lines().map { line ->
        line.substring(1, line.length - 1).split(',').associate { category ->
            val (key, value) = category.split('=')
            key.first() to value.toInt()
        }
    }

    tailrec fun Map<Char, Int>.accept(key: String): Boolean = when (key) {
        "A" -> true
        "R" -> false
        else -> {
            val rule = workflows.getValue(key).first { rule ->
                when (rule) {
                    is Send -> true
                    is Less -> getValue(rule.category) < rule.value
                    is Greater -> getValue(rule.category) > rule.value
                }
            }
            accept(rule.next)
        }
    }

    fun part1() = parts.filter { it.accept("in") }.sumOf { it.values.sum() }

    fun Map<Char, IntRange>.apply(rule: Condition): Pair<Map<Char, IntRange>, Map<Char, IntRange>> {
        val category = rule.category
        val range = getValue(category)
        val (accepted, rejected) = when (rule) {
            is Less -> range.first..<rule.value to rule.value..range.last
            is Greater -> rule.value + 1..range.last to range.first..rule.value
        }
        return this + (category to accepted) to this + (category to rejected)
    }

    fun Map<Char, IntRange>.accept(key: String): Long = when (key) {
        "A" -> values.map { it.last - it.first + 1L }.reduce(Long::times)
        "R" -> 0
        else -> workflows.getValue(key).fold(this to 0L) { (parts, acc), rule ->
            when (rule) {
                is Send -> parts to acc + parts.accept(rule.next)
                is Condition -> {
                    val (accepted, rejected) = parts.apply(rule)
                    rejected to acc + accepted.accept(rule.next)
                }
            }
        }.second
    }

    val ratings = "xmas".associate { it to 1..4000 }

    fun part2() = ratings.accept("in")
}