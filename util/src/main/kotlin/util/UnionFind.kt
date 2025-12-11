package util

class UnionFind<T : Any>() {
    private val parent = mutableMapOf<T, T>()
    private val count = mutableMapOf<T, Int>()

    val numberOfSets get() = count.size
    val sizes get() = count.values.toList()

    fun add(e: T) {
        parent[e] = e
        count[e] = 1
    }

    fun addAll(e: Iterable<T>) {
        e.forEach { add(it) }
    }

    tailrec fun find(e: T): T =
        when (val p = parent.getValue(e)) {
            e -> e
            else -> {
                parent[e] = parent.getValue(p)
                find(p)
            }
        }

    fun union(a: T, b: T) {
        val rootA = find(a)
        val rootB = find(b)

        if (rootA != rootB) {
            val sizeA = count.getValue(rootA)
            val sizeB = count.getValue(rootB)

            if (sizeA < sizeB) {
                parent[rootA] = rootB
                count[rootB] = sizeB + sizeA
                count.remove(rootA)
            } else {
                parent[rootB] = rootA
                count[rootA] = sizeA + sizeB
                count.remove(rootB)
            }
        }
    }
}