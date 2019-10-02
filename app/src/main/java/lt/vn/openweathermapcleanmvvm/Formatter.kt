@file:JvmName("Formatter")

package lt.vn.openweathermapcleanmvvm

import java.text.SimpleDateFormat
import java.util.*

fun convertLongToTimeString(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("EEE \n dd", Locale.getDefault())
    return format.format(date)
}

fun formatTemperatureString(temperature: String) = String.format("%s°", temperature)