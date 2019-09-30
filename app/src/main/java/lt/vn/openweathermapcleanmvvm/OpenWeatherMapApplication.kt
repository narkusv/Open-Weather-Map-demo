package lt.vn.openweathermapcleanmvvm

import android.app.Application
import lt.vn.openweathermapcleanmvvm.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class OpenWeatherMapApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger()
            androidContext(this@OpenWeatherMapApplication)
            modules(appModule)
        }
    }
}