package lt.vn.openweathermapcleanmvvm

import org.junit.Test

class HelperKtTests {

    @Test
    fun temperatureBelow11DegreesReturnsTemperatureTypeLow() {
        val temperatureType = mapTemperatureToTemperatureType(TEMPERATURE_LOW)
        assert(temperatureType == TemperatureType.Low)
    }

    @Test
    fun temperatureBelow20DegreesReturnsTemperatureTypeMid() {
        val temperatureType = mapTemperatureToTemperatureType(TEMPERATURE_MID)
        assert(temperatureType == TemperatureType.Mid)
    }

    @Test
    fun temperatureAbove20DegreesReturnsTemperatureTypeLow() {
        val temperatureType = mapTemperatureToTemperatureType(TEMPERATURE_HIGH)
        assert(temperatureType == TemperatureType.High)
    }

    companion object {
        const val TEMPERATURE_LOW = 8
        const val TEMPERATURE_MID = 15
        const val TEMPERATURE_HIGH = 21
    }
}