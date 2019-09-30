package lt.vn.openweathermapcleanmvvm.di

import lt.vn.openweathermapcleanmvvm.weather.WeatherViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { WeatherViewModel() }
}