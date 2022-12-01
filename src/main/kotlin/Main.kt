// Exercises For Programmers by Brian P. Hogan
// Exercise 48: Grabbing the Weather
// James Hill, Houston, Texas, 2022
// GitHub: jhill1971 Twitter: @count_BASIC

import ApiCallData.appId
import ApiCallData.baseUrl
import ApiCallData.lat
import ApiCallData.lon
import ApiCallData.units
import network.WeatherApiService
import network.WeatherResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun main() {
    /* Get user input */
    print("Please enter your latitude: ")
    val userLat = readLine().toString()
    print("Please enter your longitude (prepend - for W): ")
    val userLon = readLine().toString()
    println("Getting weather data.")
    println()

    /* Creating a Retrofit object. */
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    /* Creating a service object that will be used to make the API call. */
    val service = retrofit.create(WeatherApiService::class.java)

    /* Creating a call object that will be used to make the API call. */
    val call = service.fetchWeather(lat=userLat, lon=userLon, appId, units)

    /* Creating an anonymous class that implements the Callback interface. */
    call.enqueue(object : Callback<WeatherResponse> {

        /* This is the function that is called when the API call is successful. */
        override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
            if (response.code() == 200) {
                val weatherResponse = response.body()!!
                /* Creating a string that contains the weather data and then printing it to the console. */
                val weatherData = "City: ${weatherResponse.name}\nTemperature: ${weatherResponse.main!!.temp} Celsius\nConditions: ${weatherResponse.weather[0].description}"
                print(weatherData)
                System.exit(0)
            }
        }

        /* This is the function that is called when the API call fails. */
        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
            println("Failure" + t.message)
        }
    })


}

/** Data needed for making the API Call*/
object ApiCallData {
    val baseUrl: String = "https://api.openweathermap.org/data/"
    val lat: String = "29.9811"
    val lon: String = "-95.5675" // - indicates longitude West.
    val appId: String = "0b93efb2d3831901a5ae3c386462d3c2"
    val units: String = "metric"
}