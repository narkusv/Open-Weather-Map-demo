package lt.vn.openweathermapcleanmvvm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecasts WHERE name = :city COLLATE NOCASE")
    suspend fun getForecastByCity(city: String): ForecastEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastForCity(forecast: ForecastEntity)
}