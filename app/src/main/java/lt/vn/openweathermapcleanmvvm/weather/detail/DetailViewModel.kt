package lt.vn.openweathermapcleanmvvm.weather.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.vn.openweathermapcleanmvvm.architecture.SingleLiveEvent
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastResult
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository

class DetailViewModel(
    private val selectedCity: String,
    private val repository: WeatherRepository
) : ViewModel() {

    val weatherData = MutableLiveData<ForecastDomainModel>()
    val error = SingleLiveEvent<ForecastResult.Error>()
    val loading = MutableLiveData<Boolean>().apply { value = false }

    init {
        viewModelScope.launch {
            try {
                loading.value = true
                when (val result = repository.getForecastForCity(selectedCity)) {
                    is ForecastResult.Success -> weatherData.value = result.forecastDomainModel
                    is ForecastResult.Error -> error.value = result
                }
                loading.value = false
            } catch (ex: Exception) {
                Log.d(DetailViewModel::class.java.name, "Crash fetching data", ex)
            }
        }
    }
}