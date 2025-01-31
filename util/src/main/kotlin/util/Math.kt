package util

tailrec fun Long.gcd(other: Long): Long =
    if (other == 0L) this
    else other.gcd(this % other)

fun Long.lcm(other: Long): Long =
    (this * other) / this.gcd(other)

fun Int.pow(n: Int): Int = when {
    n == 0 -> 1
    n % 2 == 0 -> (this * this).pow(n / 2)
    else -> this * pow(n - 1)
}

fun Int.powMod(n: Int, mod: Int): Int = when {
    n == 0 -> 1
    n % 2 == 0 -> (this % mod * this % mod).pow(n / 2) % mod
    else -> this % mod * pow(n - 1) % mod
}

fun Long.pow(n: Long): Long = when {
    n == 0L -> 1L
    n % 2L == 0L -> (this * this).pow(n / 2L)
    else -> this * pow(n - 1L)
}

fun Long.powMod(n: Long, mod: Long): Long = when {
    n == 0L -> 1L
    n % 2L == 0L -> (this % mod * this % mod).pow(n / 2L) % mod
    else -> this % mod * pow(n - 1L) % mod
}

fun log(n: Int, base: Int): Int = when (base) {
    1 -> 0
    else -> {
        var result = 0
        var p = base
        while (p <= n) {
            p *= base
            result++
        }
        result
    }
}

fun log10(n: Int) = log(n, 10)

fun log(n: Long, base: Long): Long = when (base) {
    1L -> 0L
    else -> {
        var result = 0L
        var p = base
        while (p <= n) {
            p *= base
            result++
        }
        result
    }
}

fun log10(n: Long) = log(n, 10)