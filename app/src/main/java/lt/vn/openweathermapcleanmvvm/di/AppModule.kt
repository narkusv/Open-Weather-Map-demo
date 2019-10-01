package lt.vn.openweathermapcleanmvvm.di

import lt.vn.openweathermapcleanmvvm.weather.HomeViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel { HomeViewModel() }
}