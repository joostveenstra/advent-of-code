package util

val EMPTY_LINE = "\r?\n\r?\n".toRegex()
val NUMBER = "\\d+".toRegex()
val NUMBER_SIGNED = "-?\\d+".toRegex()

fun String.allInts() = findAll(NUMBER).map { it.value.toInt() }
fun String.allIntsSigned() = findAll(NUMBER_SIGNED).map { it.value.toInt() }
fun String.allLongs() = findAll(NUMBER).map { it.value.toLong() }
fun String.allLongsSigned() = findAll(NUMBER_SIGNED).map { it.value.toLong() }

fun String.match(regex: Regex) = regex.matchEntire(this)?.destructured

fun String.find(regex: Regex) = regex.find(this)?.destructured

fun String.findAll(regex: Regex) = regex.findAll(this)