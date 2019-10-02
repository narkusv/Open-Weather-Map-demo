package lt.vn.openweathermapcleanmvvm.weather.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import lt.vn.openweathermapcleanmvvm.databinding.FragmentDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment() {

    private val viewModel by viewModel<DetailViewModel> {
        parametersOf(DetailFragmentArgs.fromBundle(requireArguments()).city)
    }

    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentDetailBinding.inflate(inflater)
        dataBinding.viewModel = viewModel
        return dataBinding.root
    }
}