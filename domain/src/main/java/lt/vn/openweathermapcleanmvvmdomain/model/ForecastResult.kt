package lt.vn.openweathermapcleanmvvmdomain.model

sealed class ForecastResult {
    data class Success(val forecastDomainModel: ForecastDomainModel) : ForecastResult()
    sealed class Error : ForecastResult() {
        object ApiConfigurationError : Error()
        object CityNotFound : Error()
        object ApiCallsExceeded : Error()
        object GenericError : Error()
    }
}

data class ForecastDomainModel(
    val iconEndpoint: String,
    val description: String,
    val temperature: Int,
    val dateTime: Long,
    val name: String
)