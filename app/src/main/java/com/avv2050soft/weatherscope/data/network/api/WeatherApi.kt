package com.avv2050soft.weatherscope.data.network.api

import com.avv2050soft.weatherscope.data.network.dto.WeatherDto
import com.avv2050soft.weatherscope.domain.models.autocomplete.Autocomplete
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("forecast.json")
    suspend fun getWeather(
        @Query("key") key: String = API_KEY,

        // Pass US Zipcode, UK Postcode, Canada Postalcode, IP address,
        // Latitude/Longitude (decimal degree) or city name.
        @Query("q") location: String,
        @Query("days") days: Int = 14,
        @Query("aqi") aqi: String = "yes", //Get air quality data
        @Query("alerts") alerts: String = "yes", // Get weather alert data
        @Query("lang") language: String = "ru"
    ): WeatherDto

    @GET("search.json")
    suspend fun search(
        @Query("key") key: String = API_KEY,

        // Pass US Zipcode, UK Postcode, Canada Postalcode, IP address,
        // Latitude/Longitude (decimal degree) or city name.
        @Query("q") location: String,
    ): Autocomplete

    companion object {
        private const val API_KEY = "9efe535d12044faaa7665105231708"
        private const val BASE_URL = "https://api.weatherapi.com/v1/"

        fun create(): WeatherApi {
            val logger =
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApi::class.java)
        }
    }
}