package ru.animbus.ordomentis.data.webapi

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.animbus.ordomentis.data.webapi.interfaces.ItemApi
import ru.animbus.ordomentis.data.webapi.interfaces.UnitApi
import ru.animbus.ordomentis.data.webapi.interfaces.UserApi
import java.util.concurrent.TimeUnit

class RetrofitFactory(
    private val baseUrl: String,
    private val gson: Gson
) {
    companion object {
        private const val TIMEOUT_SECONDS = 30L
    }

    private val okHttpClient: OkHttpClient by lazy {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val original = chain.request()
                val requestBuilder = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")

                // requestBuilder.header("Authorization", "Bearer $token")

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    fun createUserApi(): UserApi = retrofit.create(UserApi::class.java)
    fun createUnitApi(): UnitApi = retrofit.create(UnitApi::class.java)
    fun createItemApi(): ItemApi = retrofit.create(ItemApi::class.java)
}