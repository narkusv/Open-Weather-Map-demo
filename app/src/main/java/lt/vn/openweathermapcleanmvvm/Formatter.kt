@file:JvmName("Formatter")

package lt.vn.openweathermapcleanmvvm

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter


fun convertLongToTimeString(time: Long): String {
    val formatter = DateTimeFormatter.ofPattern(
        "EEE\n" +
                "dd"
    )
        .withZone(ZoneId.systemDefault())
    val date = Instant.ofEpochSecond(time)
    return formatter.format(date)
}

fun formatTemperatureString(temperature: String) = String.format("%s°", temperature)