package lt.vn.openweathermapcleanmvvm.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ForecastEntity::class], version = 1, exportSchema = false)
abstract class ForecastDatabase : RoomDatabase() {

    abstract fun forecastDao(): ForecastDao
}