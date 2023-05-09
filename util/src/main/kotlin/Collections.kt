import java.util.*
import kotlin.collections.ArrayDeque
import kotlin.experimental.ExperimentalTypeInference

typealias Deque<T> = ArrayDeque<T>

fun <T> Deque<T>.push(item: T) = addFirst(item)

fun <T> Deque<T>.pop(): T = removeFirst()

fun <T> Deque<T>.peek(): T = first()

fun <T> dequeOf(vararg elements: T): Deque<T> = if (elements.isEmpty()) ArrayDeque() else ArrayDeque(elements.asList())

inline fun <T> priorityQueueOf(vararg elements: T, crossinline compareBy: (T) -> Comparable<*>?): PriorityQueue<T> =
    if (elements.isEmpty()) PriorityQueue<T>(compareBy(compareBy)) else PriorityQueue(compareBy(compareBy)).apply { addAll(elements.asList()) }

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
@SuppressWarnings
inline fun <T> Iterable<T>.productOf(selector: (T) -> Long): Long {
    var product = 1L
    for (element in this) {
        product *= selector(element)
    }
    return product
}

fun <T> List<T>.zipWithIndex(): List<Pair<T, Int>> = zip(indices)

fun <T> List<T>.permutations(): Sequence<List<T>> =
    if (isEmpty()) sequenceOf(listOf()) else sequence {
        forEach { next ->
            minus(next).permutations().forEach {
                yield(listOf(next) + it)
            }
        }
    }

fun <T> List<T>.combinations(k: Int, acc: List<T> = listOf()): Sequence<List<T>> =
    sequence {
        when (k) {
            1    -> forEach { yield(acc + it) }
            size -> yield(acc + this@combinations)
            else -> forEachIndexed { i, e -> yieldAll(subList(i + 1, size).combinations(k - 1, acc + e)) }
        }
    }

fun <T> List<List<T>>.transpose(): List<List<T>> =
    List(first().size) { j ->
        List(size) { i ->
            this[i][j]
        }
    }