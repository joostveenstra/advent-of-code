package util

import kotlin.experimental.ExperimentalTypeInference

fun Boolean.toInt() = if (this) 1 else 0

fun Iterable<Int>.product(): Int {
    var product = 1
    for (element in this) {
        product *= element
    }
    return product
}

fun Iterable<Long>.product(): Long {
    var product = 1L
    for (element in this) {
        product *= element
    }
    return product
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Int): Int {
    var product = 1
    for (element in this) {
        product *= selector(element)
    }
    return product
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    var product = 1L
    for (element in this) {
        product *= selector(element)
    }
    return product
}

inline fun <T> Iterable<T>.findIndexOf(selector: (T) -> Boolean): Int? {
    for ((i, element) in iterator().withIndex()) if (selector(element)) return i
    return null
}

fun <T> Iterable<T>.allDistinct(): Boolean {
    val set = hashSetOf<T>()
    iterator().drain { if (!set.add(it)) return false }
    return true
}

fun <T, M> Iterable<T>.allDistinctBy(map: (T) -> M): Boolean {
    val set = hashSetOf<M>()
    iterator().drain { if (!set.add(map(it))) return false }
    return true
}

fun <T> Iterable<T>.allIdentical(): Boolean {
    val iter = iterator()
    if (!iter.hasNext()) return true

    val first = iter.next()
    iter.drain { if (it != first) return false }
    return true
}

fun <T, M> Iterable<T>.allIdenticalBy(map: (T) -> M): Boolean {
    val iter = iterator()
    if (!iter.hasNext()) return true

    val first = map(iter.next())
    iter.drain { if (map(it) != first) return false }
    return true
}

fun <T> Iterable<T>.frequencies() = groupingBy { it }.eachCount()

inline fun <T> Iterator<T>.drain(use: (T) -> Unit) {
    while (hasNext()) use(next())
}

fun <T> Sequence<T>.nth(n: Int) = drop(n).first()

inline fun <T : Any> T.patternRepeating(total: Int, next: (T) -> T) =
    patternRepeating(total.toLong(), next)

inline fun <T : Any> T.patternRepeating(total: Long, next: (T) -> T): T {
    val seen = mutableMapOf<T, Long>()
    var i = 0L
    var current = this

    while (i < total) {
        if (current in seen) {
            val start = seen.getValue(current)
            val steps = i - start
            val target = start + (total - start) % steps
            return seen.entries.first { (_, i) -> i == target }.key
        }

        seen[current] = i
        current = next(current)
        i++
    }

    return current
}