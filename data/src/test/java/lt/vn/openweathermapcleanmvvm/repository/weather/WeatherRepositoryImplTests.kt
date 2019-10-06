package lt.vn.openweathermapcleanmvvm.repository.weather

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import lt.vn.openweathermapcleanmvvm.api.response.GetForecastResponse
import lt.vn.openweathermapcleanmvvm.api.response.Main
import lt.vn.openweathermapcleanmvvm.api.response.Weather
import lt.vn.openweathermapcleanmvvm.api.weather.WeatherApi
import lt.vn.openweathermapcleanmvvm.db.ForecastLocalDataSource
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastError
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

class WeatherRepositoryImplTests {

    lateinit var weatherApiMock: WeatherApi
    lateinit var weatherLocalDataSource: ForecastLocalDataSource

    lateinit var weatherRepository: WeatherRepository

    @Before
    fun before() {
        weatherApiMock = mockk()
        weatherLocalDataSource = mockk()

        weatherRepository = WeatherRepositoryImpl(weatherApiMock, weatherLocalDataSource)
    }


    @Test
    fun localDatabaseIsNotCalledIfFetchingFromApiSucceeds() {
        weatherApiMock.setSuccessGetForecastByQueryResponse()
        weatherLocalDataSource.setSuccessGetForecastResponse()
        weatherLocalDataSource.setSuccessSaveForecastResponse()
        runBlocking {
            weatherRepository.getForecastForCity(CITY)

            coVerify(exactly = 1) { weatherApiMock.getForecastByQuery(any(), any()) }
            coVerify(exactly = 0) { weatherLocalDataSource.getForecast(any()) }
            coVerify(exactly = 1) { weatherLocalDataSource.saveForecast(any()) }
            confirmVerified(weatherApiMock, weatherLocalDataSource)
        }
    }

    @Test
    fun weatherDataIsSavedToLocalDataSourceWhenFetchingFromApiSucceeds() {
        weatherApiMock.setSuccessGetForecastByQueryResponse()
        weatherLocalDataSource.setSuccessGetForecastResponse()
        weatherLocalDataSource.setSuccessSaveForecastResponse()

        runBlocking {
            weatherRepository.getForecastForCity(CITY)

            coVerify(exactly = 1) { weatherApiMock.getForecastByQuery(any(), any()) }
            coVerify(exactly = 1) { weatherLocalDataSource.saveForecast(any()) }
        }
    }

    @Test
    fun weatherIsFetchedFromLocalDataSourceWhenFetchingFromApiFails() {
        weatherApiMock.setFailedGetForecastResponse(404)
        weatherLocalDataSource.setSuccessGetForecastResponse()
        weatherLocalDataSource.setFailedGetForecastResponse()

        runBlocking {
            val forecast = weatherRepository.getForecastForCity(CITY)
            assert(forecast is Result.Error && forecast.exception is ForecastError.CityNotFound)
        }
    }

    @Test
    fun errorIsReturnedWhenFetchingFromBothDataSourcesFails() {
        weatherApiMock.setFailedGetForecastResponse(404)
        weatherLocalDataSource.setSuccessGetForecastResponse()
    }

    private fun WeatherApi.setSuccessGetForecastByQueryResponse() {
        coEvery { getForecastByQuery(any(), any()) } coAnswers {
            GetForecastResponse(
                Main(
                    20f,
                    10f,
                    15f
                ),
                listOf(
                    Weather(
                        1,
                        "Raining",
                        "bad weather",
                        "10d"
                    )
                ),
                DATE.toString(),
                CITY
            )
        }
    }

    private fun ForecastLocalDataSource.setSuccessGetForecastResponse() {
        coEvery { getForecast(any()) } coAnswers {
            Result.Success(
                ForecastDomainModel(
                    "10d",
                    "Raining",
                    TEMPERATURE,
                    DATE,
                    CITY
                )
            )
        }
    }

    private fun ForecastLocalDataSource.setFailedGetForecastResponse() {
        coEvery { getForecast(any()) } coAnswers { Result.Error(ForecastError.CityNotFound) }
    }

    private fun ForecastLocalDataSource.setSuccessSaveForecastResponse() {
        coEvery { saveForecast(any()) } coAnswers { nothing }
    }

    private fun WeatherApi.setFailedGetForecastResponse(code: Int) {
        coEvery { getForecastByQuery(any(), any()) } throws HttpException(
            Response.error<HttpException>(
                code,
                ResponseBody.create("application/json".toMediaTypeOrNull(), "")
            )
        )
    }

    companion object {
        const val TEMPERATURE = 20
        const val DATE = 1570291587L // =SAT\n05
        const val CITY = "Vilnius"
    }
}