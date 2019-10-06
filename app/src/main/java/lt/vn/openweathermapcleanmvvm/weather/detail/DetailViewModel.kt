package lt.vn.openweathermapcleanmvvm.weather.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.vn.openweathermapcleanmvvm.architecture.SingleLiveEvent
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository

class DetailViewModel(
    private val selectedCity: String,
    private val repository: WeatherRepository
) : ViewModel() {

    val weatherData = MutableLiveData<ForecastDomainModel>()
    val loading = MutableLiveData<Boolean>().apply { value = false }
    val error = SingleLiveEvent<Exception>()

    init {
        viewModelScope.launch {
            try {
                loading.value = true
                when (val result = repository.getForecastForCity(selectedCity)) {
                    is Result.Success -> weatherData.value = result.data
                    is Result.Error -> error.value = result.exception
                }
                loading.value = false
            } catch (ex: Exception) {
                error.value = ex
            }
        }
    }
}