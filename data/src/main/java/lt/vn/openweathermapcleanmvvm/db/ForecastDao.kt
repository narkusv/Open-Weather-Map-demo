package lt.vn.openweathermapcleanmvvm.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ForecastDao {

    @Query("SELECT * FROM forecasts WHERE name = :city COLLATE NOCASE")
    suspend fun getForecastByCity(city: String): ForecastEntity?

    @Query("SELECT * FROM forecasts ORDER BY dateTime DESC LIMIT :limit COLLATE NOCASE")
    suspend fun getLastCities(limit: Int): List<ForecastEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertForecastForCity(forecast: ForecastEntity)
}