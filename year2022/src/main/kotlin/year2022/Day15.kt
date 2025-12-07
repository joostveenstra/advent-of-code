package year2022

import framework.Context
import framework.Day
import util.Point
import util.allIntsSigned
import util.manhattan
import util.merge
import kotlin.math.absoluteValue

class Day15(context: Context) : Day by context {
    data class Sensor(val position: Point, val beacon: Point, val distance: Int) {
        fun findCoverageAtRow(y: Int): IntRange? {
            val dx = distance - (position.y - y).absoluteValue
            return (position.x - dx..position.x + dx).takeIf { it.first <= it.last }
        }
    }

    val sensors = lines.map { line ->
        line.allIntsSigned().toList().let { (xSensor, ySensor, xBeacon, yBeacon) ->
            val sensor = Point(xSensor, ySensor)
            val beacon = Point(xBeacon, yBeacon)
            Sensor(sensor, beacon, sensor manhattan beacon)
        }
    }

    private fun List<Sensor>.getCoverageRangesAtRow(y: Int) = mapNotNull { it.findCoverageAtRow(y) }.merge()

    fun part1(): Long {
        val row = if (isExample) 10 else 2_000_000
        return sensors.getCoverageRangesAtRow(row).sumOf { it.last - it.first }.toLong()
    }

    fun part2(): Long {
        val caveSize = if (isExample) 20 else 4_000_000
        return (0..caveSize).firstNotNullOf { y ->
            sensors.getCoverageRangesAtRow(y).takeIf { it.size > 1 }?.let { (first, second) ->
                val x = minOf(first.last, second.last) + 1
                4_000_000L * x + y
            }
        }
    }
}