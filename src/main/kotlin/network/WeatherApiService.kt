package network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/* This is the interface that will be used to make the network call. */
interface WeatherApiService {
    @GET("2.5/weather?")
    fun fetchWeather(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") appId: String?,
        @Query("units") units: String
    ): Call<WeatherResponse>

}