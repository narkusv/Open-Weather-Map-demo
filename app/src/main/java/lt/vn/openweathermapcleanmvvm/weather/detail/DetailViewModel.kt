package lt.vn.openweathermapcleanmvvm.weather.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository

class DetailViewModel(
    private val selectedCity: String,
    private val repository: WeatherRepository
) : ViewModel() {

    val loading = MutableLiveData<Boolean>().apply { value = false }

    init {
        loading.value = true
        viewModelScope.launch {
            try {
                repository.getForecastForCity(selectedCity)
                loading.value = false
            } catch (ex: Exception) {
                Log.d(DetailViewModel::class.java.name, "Crash fetching data", ex)
            }
        }
    }
}