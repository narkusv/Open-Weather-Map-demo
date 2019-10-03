package lt.vn.openweathermapcleanmvvm.weather.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import lt.vn.openweathermapcleanmvvm.R
import lt.vn.openweathermapcleanmvvm.databinding.FragmentDetailBinding
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastError
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val viewModel by viewModel<DetailViewModel> {
        parametersOf(DetailFragmentArgs.fromBundle(requireArguments()).city)
    }

    private lateinit var dataBinding: FragmentDetailBinding

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.error.observe(this, Observer { handleError(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentDetailBinding.inflate(inflater)
        dataBinding.lifecycleOwner = this
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }

    private fun handleError(error: Exception) {
        when (error) {
            is ForecastError.ApiConfigurationError,
            is ForecastError.ApiCallsExceeded,
            is ForecastError.GenericError -> {
                showSnackBarError(getString(R.string.error_generic_error))
            }
            is ForecastError.CityNotFound -> {
                showSnackBarError(getString(R.string.error_city_not_found))
            }
            else -> showSnackBarError(getString(R.string.error_generic_error)).also {
                Log.d(DetailFragment::class.java.name, "Unknown exception has been thrown", error)
            }
        }
        findNavController().popBackStack()
    }

    private fun showSnackBarError(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }
}