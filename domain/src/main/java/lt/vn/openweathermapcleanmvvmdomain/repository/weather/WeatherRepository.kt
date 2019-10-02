package lt.vn.openweathermapcleanmvvmdomain.repository.weather

import lt.vn.openweathermapcleanmvvmdomain.model.ForecastResult

interface WeatherRepository {

    suspend fun getForecastForCity(city: String): ForecastResult

}