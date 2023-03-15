import kotlin.math.absoluteValue

object Day15 : Day<Long> {
    data class Sensor(val position: Point, val beacon: Point, val distance: Int) {
        fun findCoverageAtRow(y: Int): IntRange? {
            val dx = distance - (position.y - y).absoluteValue
            return (position.x - dx..position.x + dx).takeIf { it.first <= it.last }
        }
    }

    private fun String.toSensors() = lines().map { line ->
        "-?\\d+".toRegex().findAll(line).map { it.value.toInt() }.toList().let { (xSensor, ySensor, xBeacon, yBeacon) ->
            val sensor = Point(xSensor, ySensor)
            val beacon = Point(xBeacon, yBeacon)
            Sensor(sensor, beacon, sensor.manhattanDistanceTo(beacon))
        }
    }

    private fun List<Sensor>.isExample() = maxOf { it.position.x } == 20

    private fun List<Sensor>.getCoverageRangesAtRow(y: Int) = mapNotNull { it.findCoverageAtRow(y) }.merge()

    override fun part1(input: String): Long {
        val sensors = input.toSensors()
        val row = if (sensors.isExample()) 10 else 2_000_000
        return sensors.getCoverageRangesAtRow(row).sumOf { it.last - it.first }.toLong()
    }

    override fun part2(input: String): Long {
        val sensors = input.toSensors()
        val caveSize = if (sensors.isExample()) 20 else 4_000_000
        return (0..caveSize).firstNotNullOf { y ->
            sensors.getCoverageRangesAtRow(y).takeIf { it.size > 1 }?.let { (first, second) ->
                val x = minOf(first.last, second.last) + 1
                4_000_000L * x + y
            }
        }
    }
}