package lt.vn.openweathermapcleanmvvm.weather.history

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import lt.vn.openweathermapcleanmvvm.architecture.SingleLiveEvent
import lt.vn.openweathermapcleanmvvmdomain.Result
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository

class HistoryViewModel(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val data = MutableLiveData<List<ForecastDomainModel>>()
    val error = SingleLiveEvent<Exception>()

    init {
        viewModelScope.launch {
            val result = weatherRepository.getForecastLookupHistory(5)
            if (result is Result.Success) {
                data.value = result.data
            } else if (result is Result.Error) {
                error.value = result.exception
            }
        }
    }
}