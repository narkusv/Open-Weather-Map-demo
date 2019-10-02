package lt.vn.openweathermapcleanmvvm.di

import lt.vn.openweathermapcleanmvvm.weather.HomeViewModel
import lt.vn.openweathermapcleanmvvm.weather.detail.DetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel() }
    viewModel { (city: String) -> DetailViewModel(id) }
}