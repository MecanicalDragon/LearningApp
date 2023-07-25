package net.medrag.ktapp.monitoring

import java.time.Duration

data class MonitoringConfig(
    /**
     * Enable or disable whole monitoring configuration to be applied.
     */
    val enabled: Boolean,
    /**
     * Apply bucketing configuration or not.
     */
    val sloEnabled: Boolean,
    /**
     * Apply percentile collection or not.
     */
    val percentilesEnabled: Boolean,
    /**
     * Array of durations used as SLO.
     * Example: 10ms, 25ms, 50ms, 100ms, 500ms, 1000ms, 2000ms.
     */
    val slo: Array<Duration> = emptyArray(),
    /**
     * Array of custom durations to use as SLO for specific endpoint or command.
     */
    val customSlo: Map<String, Array<Duration>> = emptyMap(),
    /**
     * Array of percentiles that will be calculated during metrics collection.
     */
    val percentiles: DoubleArray = doubleArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MonitoringConfig

        if (enabled != other.enabled) return false
        if (sloEnabled != other.sloEnabled) return false
        if (percentilesEnabled != other.percentilesEnabled) return false
        if (!slo.contentEquals(other.slo)) return false
        if (!percentiles.contentEquals(other.percentiles)) return false
        if (customSlo.size != other.customSlo.size) return false
        for (entry in customSlo) {
            if (!entry.value.contentEquals(other.customSlo[entry.key])) return false
        }

        return true
    }

    override fun hashCode(): Int {
        var result = enabled.hashCode()
        result = 31 * result + sloEnabled.hashCode()
        result = 31 * result + percentilesEnabled.hashCode()
        result = 31 * result + slo.contentHashCode()
        var mapResult = 0
        for (entry in customSlo) {
            mapResult += 31 * (entry.key.hashCode() + entry.value.contentHashCode())
        }
        result = 31 * result + mapResult
        result = 31 * result + percentiles.contentHashCode()
        return result
    }

    override fun toString(): String {
        val customMapString = StringBuilder("(")
        for (entry in customSlo) {
            customMapString.append(entry.key).append("=").append(entry.value.contentToString()).append(", ")
        }
        customMapString.delete(customMapString.length - 2, customMapString.length).append(")")
        return "MonitoringConfig(enabled=$enabled, sloEnabled=$sloEnabled, percentilesEnabled=$percentilesEnabled, " +
                "metricsSlo=${slo.contentToString()}, customSloMap=${customMapString}, " +
                "percentiles=${percentiles.contentToString()})"
    }
}
