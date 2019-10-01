package lt.vn.openweathermapcleanmvvm.weather

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

     val city = MutableLiveData<String>()

    fun onNext() {
        Log.d(HomeViewModel::class.java.name, "onNext executed, city is ${city.value}")
    }

}