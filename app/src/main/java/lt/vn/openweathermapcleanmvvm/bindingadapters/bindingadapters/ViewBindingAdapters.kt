package lt.vn.openweathermapcleanmvvm.bindingadapters.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibleIf")
fun changeVisibility(view: View, isVisible: Boolean) {
    if (isVisible && view.visibility == View.VISIBLE || !isVisible && view.visibility == View.GONE) {
        return
    }
    if (isVisible) view.visibility = View.VISIBLE else view.visibility = View.GONE
}