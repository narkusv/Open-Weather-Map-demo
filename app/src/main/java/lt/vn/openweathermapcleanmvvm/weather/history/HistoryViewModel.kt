package lt.vn.openweathermapcleanmvvm.weather.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository

class HistoryViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val data = MutableLiveData<List<ForecastDomainModel>>()

    init {
        viewModelScope.launch {
            val result = weatherRepository.getForecastForCity("Vilnius")
            if (result is Result.Success) {
                data.value = listOf(result.data)
            } else {
                // TODO fill in
            }
        }
    }
}