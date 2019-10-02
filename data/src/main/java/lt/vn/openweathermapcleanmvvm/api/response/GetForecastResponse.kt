package lt.vn.openweathermapcleanmvvm.api.response

import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel

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
    val temp: String,
    val temp_min: String,
    val temp_max: String
)

fun GetForecastResponse.toDomainModel(): ForecastDomainModel {
    return ForecastDomainModel(
        description = weather.first().description,
        temperature = main.temp,
        dateTime = dt.toLong(),
        name = name
    )
}