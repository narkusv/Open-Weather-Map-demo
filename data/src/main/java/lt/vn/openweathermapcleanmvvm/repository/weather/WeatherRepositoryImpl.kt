package lt.vn.openweathermapcleanmvvm.repository.weather

import android.util.Log
import lt.vn.openweathermapcleanmvvm.Properties
import lt.vn.openweathermapcleanmvvm.api.response.toDomainModel
import lt.vn.openweathermapcleanmvvm.api.weather.WeatherApi
import lt.vn.openweathermapcleanmvvm.db.ForecastLocalDataSource
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastError
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository
import retrofit2.HttpException

class WeatherRepositoryImpl constructor(
    private val weatherApi: WeatherApi,
    private val localDataSource: ForecastLocalDataSource
) : WeatherRepository {

    override suspend fun getForecastLookupHistory(maxElements: Int): Result<List<ForecastDomainModel>> {
        return localDataSource.getForecastLookupHistory(maxElements)
    }

    override suspend fun getForecastForCity(city: String): Result<ForecastDomainModel> {
        val remoteForecast = fetchForecast(city)
        when (remoteForecast) {
            is Result.Success -> {
                localDataSource.saveForecast(remoteForecast.data)
                return remoteForecast
            }
        }

        return when (val localForecast = localDataSource.getForecast(city)) {
            is Result.Success -> localForecast
            is Result.Error -> remoteForecast
        }
    }

    private suspend fun fetchForecast(city: String): Result<ForecastDomainModel> {
        val locationQuery = "$city, LTU"
        val units = "metric"
        return try {
            Result.Success(
                weatherApi.getForecastByQuery(locationQuery, units)
                    .toDomainModel(Properties.ICON_ENDPOINT)
            )
        } catch (exception: Exception) {
            return when ((exception as? HttpException)?.code()) {
                401 -> Result.Error(ForecastError.ApiConfigurationError)
                404 -> Result.Error(ForecastError.CityNotFound)
                429 -> Result.Error(ForecastError.ApiCallsExceeded)
                else -> Result.Error(exception)
            }
        }
    }
}