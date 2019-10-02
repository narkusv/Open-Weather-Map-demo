package lt.vn.openweathermapcleanmvvm.bindingadapters.bindingadapters

import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import lt.vn.openweathermapcleanmvvm.weather.detail.DetailViewModel

@BindingAdapter("visibleIf")
fun changeVisibility(view: View, isVisible: Boolean) {
    if (isVisible && view.visibility == View.VISIBLE || !isVisible && view.visibility == View.GONE) {
        return
    }
    if (isVisible) view.visibility = View.VISIBLE else view.visibility = View.GONE
}

@BindingAdapter("imageUrl")
fun loadImageUrlI(view: ImageView, imageUrl: String?) {
    if (!URLUtil.isValidUrl(imageUrl)) return
    Picasso.get().load(imageUrl).into(view)
}

@BindingAdapter(value = ["lowTemperatureColor", "midTemperatureColor", "highTemperatureColor", "temperatureType"])
fun setColorBasedOnTemperature(
    view: TextView,
    lowTemperatureColor: Int,
    midTemperatureColor: Int,
    highTemperatureColor: Int,
    temperatureType: DetailViewModel.TemperatureType?
) {
    if (temperatureType == null) return
    when (temperatureType) {
        is DetailViewModel.TemperatureType.Low -> view.setTextColor(lowTemperatureColor)
        is DetailViewModel.TemperatureType.Mid -> view.setTextColor(midTemperatureColor)
        is DetailViewModel.TemperatureType.High -> view.setTextColor(highTemperatureColor)
    }.apply { } // TODO consider adding exhaustive extension function
}