package util

val IntRange.width get() = last - first + 1
val UIntRange.width get() = last - first + 1u
val LongRange.width get() = last - first + 1

operator fun IntRange.contains(other: IntRange): Boolean = first <= other.first && other.last <= last

infix fun IntRange.overlaps(other: IntRange): Boolean = first <= other.last && other.first <= last

fun List<IntRange>.merge(): List<IntRange> {
    val sorted = sortedBy { it.first }
    val merged = mutableListOf(sorted.first())

    for (range in sorted.subList(1, sorted.size)) {
        val bigRange = merged.last()
        if (range.first <= bigRange.last)
            merged[merged.lastIndex] = bigRange.first..maxOf(bigRange.last, range.last)
        else
            merged.add(range)
    }

    return merged
}