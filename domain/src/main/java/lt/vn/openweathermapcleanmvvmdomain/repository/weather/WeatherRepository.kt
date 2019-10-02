package lt.vn.openweathermapcleanmvvmdomain.repository.weather

import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel

interface WeatherRepository {

    suspend fun getForecastForCity(city: String): ForecastDomainModel

}