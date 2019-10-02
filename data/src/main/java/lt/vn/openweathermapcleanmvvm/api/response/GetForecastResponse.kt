package lt.vn.openweathermapcleanmvvm.api.response

import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import kotlin.math.roundToInt

data class GetForecastResponse(
    val main: Main,
    val weather: List<Weather>,
    val dt: String,
    val name: String
)

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Main(
    val temp: Float,
    val temp_min: Float,
    val temp_max: Float
)

fun GetForecastResponse.toDomainModel(): ForecastDomainModel {
    return ForecastDomainModel(
        description = weather.first().main,
        temperature = main.temp.roundToInt(),
        dateTime = dt.toLong(),
        name = name
    )
}