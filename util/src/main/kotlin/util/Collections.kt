package util

import java.util.*
import kotlin.collections.ArrayDeque

typealias Deque<T> = ArrayDeque<T>

fun <T> Deque<T>.push(item: T) = addFirst(item)

fun <T> Deque<T>.pop(): T = removeFirst()

fun <T> Deque<T>.peek(): T = first()

fun <T> dequeOf(vararg elements: T): Deque<T> = if (elements.isEmpty()) ArrayDeque() else ArrayDeque(elements.asList())

fun <T> Collection<T>.toDeque(): Deque<T> = ArrayDeque(this)

fun <T> priorityQueueOf(vararg elements: T): PriorityQueue<T> =
    if (elements.isEmpty()) PriorityQueue<T>() else PriorityQueue<T>().apply { addAll(elements.asList()) }

inline fun <T> priorityQueueOf(vararg elements: T, crossinline by: (T) -> Comparable<*>?): PriorityQueue<T> =
    if (elements.isEmpty()) PriorityQueue<T>(compareBy(by)) else PriorityQueue(compareBy(by)).apply { addAll(elements.asList()) }

inline fun <T> Deque<T>.drain(use: (T) -> Unit) {
    while (isNotEmpty()) use(removeFirst())
}

inline fun <T> Queue<T>.drain(use: (T) -> Unit) {
    while (isNotEmpty()) use(poll())
}

fun <T> List<T>.zipWithIndex(): List<Pair<T, Int>> = zip(indices)

fun <T> Iterable<T>.permPairs(): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    forEachIndexed { i, a ->
        forEachIndexed { j, b ->
            if (i != j) result.add(a to b)
        }
    }
    return result
}

fun <T> Iterable<T>.permPairSequence(): Sequence<Pair<T, T>> = sequence {
    forEachIndexed { i, a ->
        forEachIndexed { j, b ->
            if (i != j) yield(a to b)
        }
    }
}

fun <T> Iterable<T>.permutations() = toList().permutations()
fun <T> Iterable<T>.permutations(r: Int) = toList().permutations(r)
fun <T> List<T>.permutations(r: Int = size): List<List<T>> {
    if (r > size || isEmpty()) listOf<List<T>>()

    val result = mutableListOf<List<T>>()
    val ind = indices.toMutableList()
    val cyc = (size downTo size - r).toMutableList()
    result.add(take(r + 1))

    while (true) {
        for (i in r - 1 downTo 0) {
            if (--cyc[i] == 0) {
                ind.add(ind.removeAt(i))
                cyc[i] = size - i
            } else {
                Collections.swap(ind, i, size - cyc[i])
                result.add(slice(ind.take(r)))
                break
            }

            if (i == 0) return result
        }
    }
}

fun <T> Iterable<T>.permutationSequence() = toList().permutationSequence()
fun <T> Iterable<T>.permutationSequence(r: Int) = toList().permutationSequence(r)
fun <T> List<T>.permutationSequence(r: Int = size): Sequence<List<T>> = sequence {
    if (r > size || isEmpty()) return@sequence

    val ind = indices.toMutableList()
    val cyc = (size downTo size - r).toMutableList()
    yield(take(r + 1))

    while (true) {
        for (i in r - 1 downTo 0) {
            if (--cyc[i] == 0) {
                ind.add(ind.removeAt(i))
                cyc[i] = size - i
            } else {
                Collections.swap(ind, i, size - cyc[i])
                yield(slice(ind.take(r)))
                break
            }

            if (i == 0) return@sequence
        }
    }
}

fun <T> Iterable<T>.pairs() = toList().pairs()
fun <T> List<T>.pairs(): List<Pair<T, T>> {
    val result = mutableListOf<Pair<T, T>>()
    forEachIndexed { i, a ->
        subList(i + 1, size).forEach { b ->
            result.add(a to b)
        }
    }
    return result
}

fun <T> Iterable<T>.pairSequence() = toList().pairSequence()
fun <T> List<T>.pairSequence(): Sequence<Pair<T, T>> = sequence {
    forEachIndexed { i, a ->
        subList(i + 1, size).forEach { b ->
            yield(a to b)
        }
    }
}

fun <T> Iterable<T>.combinations(k: Int) = toList().combinations(k)
fun <T> List<T>.combinations(k: Int, acc: List<T> = listOf()): List<List<T>> {
    val result = mutableListOf<List<T>>()
    when (k) {
        1 -> forEach { result.add(acc + it) }
        size -> result.add(acc + this)
        else -> forEachIndexed { i, e -> result.addAll(subList(i + 1, size).combinationSequence(k - 1, acc + e)) }
    }
    return result
}

fun <T> Iterable<T>.combinationSequence(k: Int) = toList().combinationSequence(k)
fun <T> List<T>.combinationSequence(k: Int, acc: List<T> = listOf()): Sequence<List<T>> = sequence {
    when (k) {
        1 -> forEach { yield(acc + it) }
        size -> yield(acc + this@combinationSequence)
        else -> forEachIndexed { i, e -> yieldAll(subList(i + 1, size).combinationSequence(k - 1, acc + e)) }
    }
}

fun <T> List<List<T>>.transpose(): List<List<T>> =
    List(first().size) { j ->
        List(size) { i ->
            this[i][j]
        }
    }

inline fun <T> List<T>.binarySearchFirst(predicate: (T) -> Boolean): T =
    binarySearchFirstOrNull(predicate) ?: throw IllegalStateException("No elements match predicate")

inline fun <T> List<T>.binarySearchFirstOrNull(predicate: (T) -> Boolean): T? =
    binarySearchIndexOfFirstOrNull(predicate)?.let { get(it) }

inline fun <T> List<T>.binarySearchIndexOfFirst(predicate: (T) -> Boolean): Int =
    binarySearchIndexOfFirstOrNull(predicate) ?: throw IllegalStateException("No elements match predicate")

inline fun <T> List<T>.binarySearchIndexOfFirstOrNull(predicate: (T) -> Boolean): Int? {
    var low = 0
    var high = lastIndex
    var firstFind: Int? = null
    while (low <= high) {
        val mid = (low + high) ushr 1
        if (predicate(get(mid))) {
            firstFind = mid
            high = mid - 1
        } else {
            low = mid + 1
        }
    }
    return firstFind
}

fun <T> List<T>.toPair() = this[0] to this[1]
fun <T> Pair<T, T>.toList() = listOf(first, second)
inline fun <F, T> Pair<F, F>.mapBoth(block: (F) -> T) = block(first) to block(second)
inline fun <F, S, T> Pair<F, S>.mapFirst(block: (F) -> T) = block(first) to second
inline fun <F, S, T> Pair<F, S>.mapSecond(block: (S) -> T) = first to block(second)

fun <T> List<T>.midpoint() = this[size / 2]

operator fun <T> List<T>.component6(): T = get(5)
operator fun <T> List<T>.component7(): T = get(6)
operator fun <T> List<T>.component8(): T = get(7)
operator fun <T> List<T>.component9(): T = get(8)
operator fun <T> List<T>.component10(): T = get(9)