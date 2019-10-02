package lt.vn.openweathermapcleanmvvm.weather.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailViewModel(
    private val selectedCity: String
) : ViewModel() {

    val loading = MutableLiveData<Boolean>().apply { value = false }

    init {

    }
}