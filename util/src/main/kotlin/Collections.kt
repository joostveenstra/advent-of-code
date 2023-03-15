import kotlin.experimental.ExperimentalTypeInference

typealias Deque<T> = ArrayDeque<T>

fun <T> Deque<T>.push(item: T) = addFirst(item)

fun <T> Deque<T>.pop(): T = removeFirst()

fun <T> Deque<T>.peek(): T = first()

fun <T> dequeOf(vararg elements: T): Deque<T> = if (elements.size == 0) ArrayDeque() else ArrayDeque(elements.asList())

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