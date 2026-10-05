package year2021

import framework.Context
import framework.Day
import util.productOf
import util.toInt

class Day16(context: Context) : Day by context {
    sealed class Packet(val version: Int)
    class Literal(version: Int, val value: Long) : Packet(version)
    class Operator(version: Int, val typeId: Int, val packets: List<Packet>) : Packet(version)

    val bits = input.map { it.digitToInt(16).toString(2).padStart(4, '0') }.joinToString("").iterator()

    fun Iterator<Char>.nextInt(size: Int) = (1..size).fold(0) { acc, _ -> (acc shl 1) or next().digitToInt() }
    fun Iterator<Char>.next(size: Int) = (1..size).map { next() }.joinToString("")

    fun parsePacket(bits: Iterator<Char>): Packet {
        val version = bits.nextInt(3)
        return when (val typeId = bits.nextInt(3)) {
            4 -> parseLiteral(version, bits)
            else -> parseOperator(version, typeId, bits)
        }
    }

    fun parseLiteral(version: Int, bits: Iterator<Char>): Literal {
        fun parseValue(value: Long = 0): Long {
            val hasNext = bits.nextInt(1) == 1
            val value = (value shl 4) or bits.nextInt(4).toLong()
            return if (hasNext) parseValue(value) else value
        }

        return Literal(version, parseValue())
    }

    fun parseOperator(version: Int, typeId: Int, bits: Iterator<Char>): Operator {
        val subPackets = if (bits.nextInt(1) == 0) {
            val size = bits.nextInt(15)
            val subBits = bits.next(size).iterator()
            buildList { while (subBits.hasNext()) add(parsePacket(subBits)) }
        } else {
            val amount = bits.nextInt(11)
            (1..amount).map { parsePacket(bits) }
        }

        return Operator(version, typeId, subPackets)
    }
    
    fun Packet.sumVersions(): Int = when (this) {
        is Literal -> version
        is Operator -> version + packets.sumOf { it.sumVersions() }
    }
    
    fun Packet.evaluate(): Long = when (this) {
        is Literal -> value
        is Operator -> when (typeId) {
            0 -> packets.sumOf { it.evaluate() }
            1 -> packets.productOf { it.evaluate() }
            2 -> packets.minOf { it.evaluate() }
            3 -> packets.maxOf { it.evaluate() }
            5 -> (packets.first().evaluate() > packets.last().evaluate()).toInt()
            6 -> (packets.first().evaluate() < packets.last().evaluate()).toInt()
            7 -> (packets.first().evaluate() == packets.last().evaluate()).toInt()
            else -> error("Unknown packet type")
        }.toLong()
    }
    
    val packet = parsePacket(bits)

    fun part1() = packet.sumVersions()
    fun part2() = packet.evaluate()
}