package util

val IntRange.width get() = last - first + 1
val UIntRange.width get() = last - first + 1u
val LongRange.width get() = last - first + 1

operator fun IntRange.contains(other: IntRange): Boolean = first <= other.first && other.last <= last

infix fun IntRange.overlaps(other: IntRange): Boolean = first <= other.last && other.first <= last

@JvmName("mergeIntRange")
fun Iterable<IntRange>.merge() = mergeRanges(
    first = IntRange::first,
    last = IntRange::last,
    builder = Int::rangeTo
)

@JvmName("mergeLongRange")
fun Iterable<LongRange>.merge() = mergeRanges(
    first = LongRange::first,
    last = LongRange::last,
    builder = Long::rangeTo
)

private inline fun <T, V : Comparable<V>> Iterable<T>.mergeRanges(
    crossinline first: T.() -> V,
    last: T.() -> V,
    builder: (V, V) -> T
): List<T> {
    val sorted = sortedBy(first).iterator()
    if (!sorted.hasNext()) return emptyList()
    val merged = mutableListOf(sorted.next())

    sorted.drain { range ->
        val bigRange = merged.last()
        val last = bigRange.last()
        if (range.first() <= last)
            merged[merged.lastIndex] = builder(bigRange.first(), maxOf(last, range.last()))
        else
            merged.add(range)
    }

    return merged
}