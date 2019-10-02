package lt.vn.openweathermapcleanmvvm

import okhttp3.Interceptor
import okhttp3.Response

class ProvideApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val updatedUrl = request.url.newBuilder().addQueryParameter("APPID", apiKey).build()
        val newRequest = request.newBuilder().url(updatedUrl).build()
        return chain.proceed(newRequest)
    }
}