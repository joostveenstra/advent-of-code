package util

val EMPTY_LINE = "\r?\n\r?\n".toRegex()

fun String.allInts() = "-?\\d+".toRegex().findAll(this).map { it.value.toInt() }
fun String.allLongs() = "-?\\d+".toRegex().findAll(this).map { it.value.toLong() }

fun String.match(regex: Regex) = regex.matchEntire(this)?.destructured

fun String.find(regex: Regex) = regex.find(this)?.destructured

fun String.findAll(regex: Regex) = regex.findAll(this)