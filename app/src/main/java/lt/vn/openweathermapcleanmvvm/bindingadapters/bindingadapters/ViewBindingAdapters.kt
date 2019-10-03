package lt.vn.openweathermapcleanmvvm.bindingadapters.bindingadapters

import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import lt.vn.openweathermapcleanmvvm.TemperatureType
import lt.vn.openweathermapcleanmvvm.mapTemperatureToTemperatureType

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

@BindingAdapter(value = ["lowTemperatureColor", "midTemperatureColor", "highTemperatureColor", "temperature"])
fun setColorBasedOnTemperature(
    view: TextView,
    lowTemperatureColor: Int,
    midTemperatureColor: Int,
    highTemperatureColor: Int,
    temperature: Int?
) {
    if (temperature == null) return
    when (mapTemperatureToTemperatureType(temperature)) {
        is TemperatureType.Low -> view.setTextColor(lowTemperatureColor)
        is TemperatureType.Mid -> view.setTextColor(midTemperatureColor)
        is TemperatureType.High -> view.setTextColor(highTemperatureColor)
    }.apply { } // TODO consider adding exhaustive extension function
}