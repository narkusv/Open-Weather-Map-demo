package lt.vn.openweathermapcleanmvvm.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastError

class ForecastLocalDataSource internal constructor(
    private val forecastDao: ForecastDao
) {
    suspend fun getForecast(city: String): Result<ForecastDomainModel> =
        withContext(Dispatchers.IO) {
            try {
                val forecast = forecastDao.getForecastByCity(city)?.toDomainModel()
                if (forecast != null) {
                    Result.Success(forecast)
                } else {
                    Result.Error(ForecastError.CityNotFound)
                }
            } catch (e: Exception) {
                Result.Error(ForecastError.GenericError(e))
            }
        }

    suspend fun getForecastLookupHistory(size: Int): Result<List<ForecastDomainModel>> =
        withContext(Dispatchers.IO) {
            try {
                val forecasts: List<ForecastDomainModel> =
                    forecastDao.getLastCities(size).filterNotNull().map {
                        it.toDomainModel()
                    }
                if (forecasts.isNotEmpty()) {
                    Result.Success(forecasts)
                } else {
                    Result.Error(ForecastError.CityNotFound)
                }
            } catch (e: Exception) {
                Result.Error(ForecastError.GenericError(e))
            }
        }

    suspend fun saveForecast(forecast: ForecastDomainModel) =
        withContext(Dispatchers.IO) {
            forecastDao.insertForecastForCity(forecast.toDatabaseEntity())
        }
}