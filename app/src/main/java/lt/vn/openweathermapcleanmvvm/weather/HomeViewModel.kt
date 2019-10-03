package lt.vn.openweathermapcleanmvvm.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lt.vn.openweathermapcleanmvvm.architecture.SingleLiveEvent

class HomeViewModel : ViewModel() {

    val error = SingleLiveEvent<Error>()
    val showWeatherForCity = SingleLiveEvent<ShowWeatherForCity>()
    val showWeatherHistory = SingleLiveEvent<Unit>()
    val city = MutableLiveData<String>().apply { value = "" }

    fun onNext() {
        if (!isValidCityName(city.value ?: "")) {
            error.value = Error.InvalidCityName
            return
        }
        showWeatherForCity.value = ShowWeatherForCity(city.value!!)
    }

    fun onOpenHistory() {
        showWeatherHistory()
    }

    private fun isValidCityName(city: String) = city.isNotEmpty()

    data class ShowWeatherForCity(val city: String)
    sealed class Error {
        object InvalidCityName : Error()
    }
}