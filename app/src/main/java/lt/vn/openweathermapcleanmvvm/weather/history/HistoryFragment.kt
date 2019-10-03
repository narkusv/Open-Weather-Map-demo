package lt.vn.openweathermapcleanmvvm.weather.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import lt.vn.openweathermapcleanmvvm.databinding.FragmentHistoryBinding
import org.koin.android.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {

    private lateinit var dataBinding: FragmentHistoryBinding
    private val viewModel by viewModel<HistoryViewModel>()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.data.observe(this, Observer {
            (dataBinding.rvHistory.adapter as ForecastHistoryAdapter).submitList(it)
        })
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
}