package lt.vn.openweathermapcleanmvvmdomain.model

sealed class ForecastError : Exception() {
    object ApiConfigurationError : ForecastError()
    object CityNotFound : ForecastError()
    object ApiCallsExceeded : ForecastError()
    data class GenericError(val throwable: Throwable) : ForecastError()
}

data class ForecastDomainModel(
    val iconEndpoint: String,
    val description: String,
    val temperature: Int,
    val dateTime: Long,
    val name: String
)