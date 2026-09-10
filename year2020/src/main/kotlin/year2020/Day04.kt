package year2020

import framework.Context
import framework.Day
import util.EMPTY_LINE

class Day04(context: Context) : Day by context {
    val hclRegex = "#[0-9a-f]{6}".toRegex()
    val passports = input.split(EMPTY_LINE).map { p ->
        p.split(":|\\s+".toRegex()).chunked(2).filter { (key) -> key != "cid" }
    }

    fun String.inRange(r: IntRange) = toInt() in r

    fun validateField(key: String, value: String) = when (key) {
        "byr" -> value.inRange(1920..2002)
        "iyr" -> value.inRange(2010..2020)
        "eyr" -> value.inRange(2020..2030)
        "hgt" -> value.isValidHeight()
        "hcl" -> value matches hclRegex
        "ecl" -> value in setOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")
        "pid" -> value.length == 9 && value.all { it.isDigit() }
        else -> false
    }

    fun String.isValidHeight() = when (takeLast(2)) {
        "cm" -> dropLast(2).inRange(150..193)
        "in" -> dropLast(2).inRange(59..76)
        else -> false
    }

    fun part1() = passports.count { it.size == 7 }
    fun part2() = passports.count { it.size == 7 && it.all { (key, value) -> validateField(key, value) } }
}