package lt.vn.openweathermapcleanmvvm.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import lt.vn.openweathermapcleanmvvmdomain.model.ForecastDomainModel

@Entity(tableName = "Forecasts")
data class ForecastEntity(
    @PrimaryKey @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "iconEndpoint") val iconEndpoint: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "temperature") val temperature: Int,
    @ColumnInfo(name = "dateTime") val dateTime: Long
)

fun ForecastEntity.toDomainModel(): ForecastDomainModel {
    return ForecastDomainModel(
        iconEndpoint = iconEndpoint,
        description = description,
        temperature = temperature,
        dateTime = dateTime,
        name = name
    )
}

fun ForecastDomainModel.toDatabaseEntity(): ForecastEntity {
    return ForecastEntity(
        iconEndpoint = iconEndpoint,
        description = description,
        temperature = temperature,
        dateTime = dateTime,
        name = name
    )
}