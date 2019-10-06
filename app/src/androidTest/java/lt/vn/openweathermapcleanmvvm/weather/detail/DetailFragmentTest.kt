package lt.vn.openweathermapcleanmvvm.weather.detail

import androidx.annotation.StringRes
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import io.mockk.coEvery
import io.mockk.mockk
import lt.vn.openweathermapcleanmvvm.R
import lt.vn.openweathermapcleanmvvm.di.appModule
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastError
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
@LargeTest
class DetailFragmentTest : KoinTest {

    private lateinit var mockWeatherRepository: WeatherRepository

    @Before
    fun setup() {
        mockWeatherRepository = mockk()

        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(appModule)
            androidLogger()
        }

        loadKoinModules(
            module {
                single { mockWeatherRepository }
            }
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun verifyElements() {
        mockWeatherRepository.setSuccessWeatherRepositoryResponse()

        launchFragmentInContainer<DetailFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = DetailFragmentArgs(CITY).toBundle()
        )

        onView(withId(R.id.view_weather_item))
            .check(matches(isDisplayed()))
    }

    @Test
    fun uiDataIsPrefilledWhenDataIsReturnedFromRepository() {
        mockWeatherRepository.setSuccessWeatherRepositoryResponse()

        launchFragmentInContainer<DetailFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = DetailFragmentArgs(CITY).toBundle()
        )

        onView(withId(R.id.tvTemperature)).check(matches(withSubstring("20°")))
        onView(withId(R.id.tvCity)).check(matches(withText(CITY)))
        onView(withId(R.id.tvWeatherStatus)).check(matches(withText(DESCRIPTION)))
        onView(withId(R.id.tvDate)).check(matches(withText(DATE_FORMATTED)))
    }

    @Test
    fun correctColoursAreSetOnElementsDependingOnTemperature() {
        mockWeatherRepository.setSuccessWeatherRepositoryResponse()

        launchFragmentInContainer<DetailFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = DetailFragmentArgs(CITY).toBundle()
        )

        onView(withId(R.id.tvTemperature)).check(matches(hasTextColor(EXPECTED_TEMPERATURE_COLOR)))
        onView(withId(R.id.tvCity)).check(matches(hasTextColor(EXPECTED_CITY_COLOR)))
        onView(withId(R.id.tvDate)).check(matches(hasTextColor(EXPECTED_DATE_COLOR)))
    }

    @Test
    fun cityNotFoundErrorMessageIsShownWhenRepositoryReturnsCityNotFoundError() {
        mockWeatherRepository.setCityNotFoundWeatherRepositoryResponse()

        launchFragmentInContainer<DetailFragment>(
            themeResId = R.style.AppTheme,
            fragmentArgs = DetailFragmentArgs(CITY).toBundle()
        )

        checkSnackBarText(R.string.error_city_not_found)
    }

    private fun checkSnackBarText(@StringRes resource: Int) {
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(resource)))
    }

    private fun WeatherRepository.setSuccessWeatherRepositoryResponse() {
        coEvery { getForecastForCity(any()) } coAnswers {
            Result.Success(
                ForecastDomainModel(
                    ICON_ENDPOINT,
                    DESCRIPTION,
                    TEMPERATURE,
                    DATE,
                    CITY
                )
            )
        }
    }

    private fun WeatherRepository.setCityNotFoundWeatherRepositoryResponse() {
        coEvery { getForecastForCity(any()) } coAnswers { Result.Error(ForecastError.CityNotFound) }
    }

    companion object {
        const val ICON_ENDPOINT = "http://openweathermap.org/img/wn/01d@2x.png"
        const val DESCRIPTION = "Rain"
        const val TEMPERATURE = 20
        const val EXPECTED_DATE_COLOR = R.color.yellow
        const val EXPECTED_CITY_COLOR = R.color.green
        const val EXPECTED_TEMPERATURE_COLOR = R.color.red
        const val DATE = 1570291587L // =SAT\n05
        const val DATE_FORMATTED = "Sat\n05"
        const val CITY = "Vilnius"
    }
}