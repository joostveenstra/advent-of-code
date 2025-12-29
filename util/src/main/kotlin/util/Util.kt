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

inline fun <T> MutableList<T>.mapInPlace(transform: (T) -> T) = forEachIndexed { idx, t -> this[idx] = transform(t) }
inline fun <T> MutableList<T>.mapInPlaceIndexed(transform: (idx: Int, T) -> T) = forEachIndexed { idx, t -> this[idx] = transform(idx, t) }

inline fun <T> Array<T>.mapInPlace(transform: (T) -> T) = forEachIndexed { idx, t -> this[idx] = transform(t) }
inline fun IntArray.mapInPlace(transform: (Int) -> Int) = forEachIndexed { idx, t -> this[idx] = transform(t) }

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

fun <T> Iterable<Iterable<T>>.transpose(forceDrain: Boolean = true) = buildList {
    val iterators = this@transpose.map { it.iterator() }
    while (iterators.all { it.hasNext() }) add(iterators.map { it.next() })
    if (forceDrain && iterators.any { it.hasNext() }) error("Iterators were not drained while swapping")
}

fun <T> Sequence<Iterable<T>>.transpose(forceDrain: Boolean = true) = sequence {
    val iterators = this@transpose.map { it.iterator() }.toList()
    while (iterators.all { it.hasNext() }) yield(iterators.map { it.next() })
    if (forceDrain && iterators.any { it.hasNext() }) error("Iterators were not drained while swapping")
}

fun <T> Sequence<T>.split(predicate: (T) -> Boolean): Sequence<List<T>> = sequence {
    var current = mutableListOf<T>()

    for (element in this@split) {
        if (predicate(element)) {
            if (current.isNotEmpty()) yield(current)
            current = mutableListOf()
        } else {
            current += element
        }
    }

    if (current.isNotEmpty()) yield(current)
}

fun <T> Sequence<T>.split(value: T): Sequence<List<T>> = split { it == value }

fun <T> Iterable<T>.split(predicate: (T) -> Boolean): List<List<T>> {
    val result = mutableListOf<List<T>>()
    var current = mutableListOf<T>()

    for (element in this) {
        if (predicate(element)) {
            if (current.isNotEmpty()) result += current
            current = mutableListOf()
        } else {
            current += element
        }
    }

    if (current.isNotEmpty()) result += current
    return result
}

fun <T> Iterable<T>.split(value: T): List<List<T>> = split { it == value }

fun <T> Iterable<T>.frequencies() = groupingBy { it }.eachCount()
fun <T> Sequence<T>.frequencies() = groupingBy { it }.eachCount()
fun CharSequence.frequencies() = groupingBy { it }.eachCount()

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