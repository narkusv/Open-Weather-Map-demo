package lt.vn.openweathermapcleanmvvm.weather

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import lt.vn.openweathermapcleanmvvm.R

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        val navController = findNavController(R.id.nav_host_fragment)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment))
        toolbar.apply {
            setupWithNavController(
                navController,
                appBarConfiguration
            )
            visibility = View.GONE
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment) {
                toolbar.visibility = View.GONE
                return@addOnDestinationChangedListener
            } else {
                toolbar.visibility = View.VISIBLE
                toolbar.setNavigationIcon(R.drawable.ic_arrow_flipped)
            }
        }
    }
}