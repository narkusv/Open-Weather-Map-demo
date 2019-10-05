package lt.vn.openweathermapcleanmvvmdomain.repository.weather

import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel

interface WeatherRepository {

    suspend fun getForecastForCity(city: String): Result<ForecastDomainModel>

    suspend fun getForecastLookupHistory(maxElements: Int): Result<List<ForecastDomainModel>>
}