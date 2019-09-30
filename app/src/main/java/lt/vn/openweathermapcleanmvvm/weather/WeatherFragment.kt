package lt.vn.openweathermapcleanmvvm.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import lt.vn.openweathermapcleanmvvm.BR
import lt.vn.openweathermapcleanmvvm.R
import lt.vn.openweathermapcleanmvvm.databinding.FragmentWeatherBinding
import org.koin.android.viewmodel.ext.android.getViewModel

class WeatherFragment : Fragment() {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var dataBinding: FragmentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onObserve(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_weather, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable(BR.viewModel, viewModel)
        return dataBinding.root
    }

    private fun onObserve(viewModel: WeatherViewModel) {

    }
}