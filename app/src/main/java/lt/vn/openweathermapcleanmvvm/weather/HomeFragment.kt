package lt.vn.openweathermapcleanmvvm.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import lt.vn.openweathermapcleanmvvm.BR
import lt.vn.openweathermapcleanmvvm.R
import lt.vn.openweathermapcleanmvvm.databinding.FragmentHomeBinding
import lt.vn.openweathermapcleanmvvm.weather.detail.DetailFragmentArgs
import org.koin.android.viewmodel.ext.android.getViewModel


class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var dataBinding: FragmentHomeBinding

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
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        dataBinding.lifecycleOwner = this
        dataBinding.setVariable(BR.viewModel, viewModel)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etCity.apply {
            requestFocus()
            setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.onNext()
                true
            } else {
                false
            }
        }
        }
    }

    private fun onObserve(viewModel: HomeViewModel) {
        viewModel.error.observe(this, Observer {
            when (it) {
                HomeViewModel.Error.InvalidCityName -> handleInvalidCityNameError()
            }
        })
        viewModel.showWeatherForCity.observe(this, Observer {
            findNavController().navigate(R.id.action_homeFragment_to_detailFragment, DetailFragmentArgs(it.city).toBundle())
        })
    }

    private fun handleInvalidCityNameError() {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            R.string.error_city_name_invalid,
            Snackbar.LENGTH_SHORT
        )
            .show()
    }
}