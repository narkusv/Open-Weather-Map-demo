package lt.vn.openweathermapcleanmvvmdomain.model

data class ForecastDomainModel(
    val description: String,
    val temperature: String,
    val dateTime: Long,
    val name: String
)