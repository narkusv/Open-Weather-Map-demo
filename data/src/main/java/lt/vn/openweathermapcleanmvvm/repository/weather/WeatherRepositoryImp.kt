package lt.vn.openweathermapcleanmvvm.repository.weather

import android.util.Log
import lt.vn.openweathermapcleanmvvm.api.response.toDomainModel
import lt.vn.openweathermapcleanmvvm.api.weather.WeatherApi
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository

class WeatherRepositoryImpl constructor(
    private val weatherApi: WeatherApi
) : WeatherRepository {

    override suspend fun getForecastForCity(city: String): ForecastDomainModel {
        val locationQuery = "$city, LTU"
        try {
            return weatherApi.getForecastByQuery(locationQuery).toDomainModel()
        } catch (ex: Exception) {
            Log.d(WeatherRepository::class.java.name, "Error retrieving service result", ex)
            throw ex
        }
    }
}