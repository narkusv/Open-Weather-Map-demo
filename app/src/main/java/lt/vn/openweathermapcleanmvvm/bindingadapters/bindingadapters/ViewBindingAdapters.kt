package lt.vn.openweathermapcleanmvvm.bindingadapters.bindingadapters

import android.view.View
import android.webkit.URLUtil
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

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