package lt.vn.openweathermapcleanmvvm

fun mapTemperatureToTemperatureType(temperature: Int): TemperatureType {
    return when {
        temperature <= 10 -> TemperatureType.Low
        temperature >= 20 -> TemperatureType.High
        else -> TemperatureType.Mid
    }
}

sealed class TemperatureType {
    object Low : TemperatureType()
    object Mid : TemperatureType()
    object High : TemperatureType()
}