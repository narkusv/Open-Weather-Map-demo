package lt.vn.openweathermapcleanmvvm.weather.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import lt.vn.openweathermapcleanmvvm.convertLongToTimeString
import lt.vn.openweathermapcleanmvvm.databinding.ViewWeatherItemBinding
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel

class ForecastHistoryAdapter :
    ListAdapter<ForecastDomainModel, ForecastHistoryAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ViewWeatherItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ForecastDomainModel) {

            binding.apply {
                city = item.name
                iconEndpoint = item.iconEndpoint
                status = item.description
                temperature = item.temperature
                date = convertLongToTimeString(item.dateTime)
                executePendingBindings()
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ViewWeatherItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }
}

class TaskDiffCallback : DiffUtil.ItemCallback<ForecastDomainModel>() {
    override fun areItemsTheSame(
        oldItem: ForecastDomainModel,
        newItem: ForecastDomainModel
    ): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(
        oldItem: ForecastDomainModel,
        newItem: ForecastDomainModel
    ): Boolean {
        return oldItem == newItem
    }
}