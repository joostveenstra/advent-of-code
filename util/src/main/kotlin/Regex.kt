val EMPTY_LINE = "\r?\n\r?\n".toRegex()

fun String.allInts() = "-?\\d+".toRegex().findAll(this).map { it.value.toInt() }