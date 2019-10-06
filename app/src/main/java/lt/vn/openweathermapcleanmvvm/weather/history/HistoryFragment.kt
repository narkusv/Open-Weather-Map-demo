package lt.vn.openweathermapcleanmvvm.weather.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import lt.vn.openweathermapcleanmvvm.R
import lt.vn.openweathermapcleanmvvm.databinding.FragmentHistoryBinding
import lt.vn.openweathermapcleanmvvm.weather.detail.DetailFragment
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastError
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private lateinit var dataBinding: FragmentHistoryBinding
    private val viewModel by viewModel<HistoryViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(this, Observer {
            (dataBinding.rvHistory.adapter as ForecastHistoryAdapter).submitList(it)
        })
        viewModel.error.observe(this, Observer { handleError(it) })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentHistoryBinding.inflate(inflater)
        dataBinding.viewModel = viewModel
        dataBinding.lifecycleOwner = this
        dataBinding.rvHistory.apply {
            adapter = ForecastHistoryAdapter()
            layoutManager = LinearLayoutManager(context)
        }
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
                showSnackBarError(getString(R.string.error_history_empty))
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