package lt.vn.openweathermapcleanmvvm.api.weather

import lt.vn.openweathermapcleanmvvm.api.response.GetForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather")
    suspend fun getForecastByQuery(
        @Query("q") query: String,
        @Query("units") units: String
    ): GetForecastResponse

}