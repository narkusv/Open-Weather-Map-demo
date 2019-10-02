package lt.vn.openweathermapcleanmvvm

import lt.vn.openweathermapcleanmvvm.api.weather.WeatherApi
import lt.vn.openweathermapcleanmvvm.repository.weather.WeatherRepositoryImpl
import lt.vn.openweathermapcleanmvvmdomain.repository.weather.WeatherRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single { provideDefaultOkhttpClient() }
    single {
        createWebService<WeatherApi>(
            get(),
            Properties.BASE_URL
        )
    }
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }

}

fun provideDefaultOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(ProvideApiKeyInterceptor(Properties.API_KEY))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}

object Properties {
    const val BASE_URL = "https://api.openweathermap.org/"
    const val API_KEY = "6138bc9b33cfa45c4e1bfe3d8709b8fd"
}