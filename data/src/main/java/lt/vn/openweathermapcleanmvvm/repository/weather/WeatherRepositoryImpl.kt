package lt.vn.openweathermapcleanmvvm.repository.weather

import android.util.Log
import lt.vn.openweathermapcleanmvvm.Properties
import lt.vn.openweathermapcleanmvvm.api.response.toDomainModel
import lt.vn.openweathermapcleanmvvm.api.weather.WeatherApi
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastResult
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository
import retrofit2.HttpException

class WeatherRepositoryImpl constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getForecastForCity(city: String): ForecastResult {
        val locationQuery = "$city, LTU"
        val units = "metric"
        return try {
            ForecastResult.Success(
                weatherApi.getForecastByQuery(locationQuery, units)
                    .toDomainModel(Properties.ICON_ENDPOINT)
            )
        } catch (exception: Exception) {
            Log.d(WeatherRepository::class.java.name, "Error retrieving service result", exception)
            when ((exception as HttpException).code()) {
                401 -> ForecastResult.Error.ApiConfigurationError
                404 -> ForecastResult.Error.CityNotFound
                429 -> ForecastResult.Error.ApiCallsExceeded
                else -> ForecastResult.Error.GenericError
            }
        }
    }
}