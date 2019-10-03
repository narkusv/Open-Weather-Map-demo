package lt.vn.openweathermapcleanmvvm.di

import lt.vn.openweathermapcleanmvvm.weather.HomeViewModel
import lt.vn.openweathermapcleanmvvm.weather.detail.DetailViewModel
import lt.vn.openweathermapcleanmvvm.weather.history.HistoryViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel() }
    viewModel { HistoryViewModel(get()) }
    viewModel { (city: String) -> DetailViewModel(city, get()) }
}