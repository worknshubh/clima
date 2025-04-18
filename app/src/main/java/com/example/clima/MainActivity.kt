package com.example.clima

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {
    private lateinit var tempcurrent:TextView
    private lateinit var wind_speed:TextView
    private lateinit var humidity:TextView
    private lateinit var chance_of_rain:TextView
    private lateinit var weatherinfo:TextView
    private lateinit var miniboxcurrenttime:TextView
    private lateinit var miniboxcurrenttemp:TextView
    private lateinit var miniboxcurrentplusone:TextView
    private lateinit var miniboxplusonetemp:TextView
    private lateinit var miniboxcurrentplustwo:TextView
    private lateinit var miniboxplustwotemp:TextView
    private lateinit var miniboxcurrentplusthree:TextView
    private lateinit var miniboxplusthreetemp:TextView
    private lateinit var todayforecast:TextView
    private lateinit var tomorrowforecast:TextView
    private lateinit var dftforecast:TextView
    private var list_index by Delegates.notNull<Int>()
    private lateinit var apikey:String
    private lateinit var city:String
    private lateinit var days:String
    private lateinit var placename:TextView
    private lateinit var datetoday:TextView
    private lateinit var lottieAnimationView: LottieAnimationView
            @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
                var user_name = findViewById<TextView>(R.id.user_name)
        tempcurrent = findViewById(R.id.tempcurrent)
        wind_speed = findViewById(R.id.windspeed)
        humidity = findViewById(R.id.humidity)
        chance_of_rain = findViewById(R.id.rain)
        weatherinfo = findViewById(R.id.weatherinfo)
        miniboxcurrenttime = findViewById(R.id.miniboxcurrenttime)
        miniboxcurrenttemp = findViewById(R.id.miniboxcurrenttemp)
        miniboxcurrentplusone = findViewById(R.id.currentplusone)
        miniboxplusonetemp = findViewById(R.id.miniboxplusonetemp)
        miniboxcurrentplustwo = findViewById(R.id.miniboxcurrentplustwo)
        miniboxplustwotemp = findViewById(R.id.miniboxplustwotemp)
        miniboxcurrentplusthree = findViewById(R.id.miniboxcurrenttimeplusthree)
         miniboxplusthreetemp = findViewById(R.id.miniboxplusthreetemp)
        todayforecast = findViewById(R.id.todayforecast)
        tomorrowforecast = findViewById(R.id.tomorrowforecast)
        dftforecast = findViewById(R.id.dftforecast)
                placename = findViewById(R.id.place_name)
                datetoday = findViewById(R.id.date)
                lottieAnimationView = findViewById(R.id.lottieanimation)
        list_index = 0
        var searchfield = findViewById<EditText>(R.id.searchfield)
        var searchbtn = findViewById<ImageView>(R.id.searchicon)
        apikey = constant.api_key
                user_name.text = constant.username
        city = "Kolkata"
        days = "3"
        todayforecast.setTextColor(ContextCompat.getColor(this,R.color.mygrey))
                fetchweatherdata()

                searchbtn.setOnClickListener{
                    if(searchfield.text.isNotEmpty()){
                        city = searchfield.text.toString()
                        fetchweatherdata()
                        searchfield.text.clear()

                        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(searchfield.windowToken, 0)
                    }
                }

    }

    fun fetchweatherdata() {
        val call = retrofitinstance.instance.getWeather(apikey, city, days)
        call.enqueue(object : Callback<weatherdata> {
            override fun onResponse(call: Call<weatherdata>, response: Response<weatherdata>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val temprature = responseBody?.current?.temp_c.toString()
                    val overview = responseBody?.current?.condition?.text.toString()
                    val humiditycurremt = responseBody?.current?.humidity.toString()
                    val windspeed = responseBody?.current?.wind_kph.toString()
                    val location_city = responseBody?.location?.name.toString()
                    val location_region = responseBody?.location?.region.toString()
                    val chanceofrain =
                        responseBody?.forecast?.forecastday?.get(0)?.day?.daily_chance_of_rain
                    val currentHour = java.time.LocalTime.now().hour
                    val date_today = responseBody?.forecast?.forecastday?.get(0)?.date
                    datetoday.text = date_today
                    placename.text = "$location_city, $location_region"

                    when(overview){
                        "Mist","Fog","Haze"->{lottieAnimationView.setAnimation(R.raw.mist)
                            lottieAnimationView.playAnimation()
                        }
                        "Clear"->{lottieAnimationView.setAnimation(R.raw.nightclear)
                            lottieAnimationView.playAnimation()
                        }
                        "Sunny"->{lottieAnimationView.setAnimation(R.raw.sunny)
                            lottieAnimationView.playAnimation()
                        }
                        "Cloudy"->{lottieAnimationView.setAnimation(R.raw.cloudy)
                            lottieAnimationView.playAnimation()
                        }
                        "Light rain","Moderate rain","Heavy rain","Drizzle","Light rain shower","Moderate or heavy rain shower","Torrential rain shower","Patchy light rain","Moderate or heavy rain with thunder"->{lottieAnimationView.setAnimation(R.raw.rain)
                            lottieAnimationView.playAnimation()
                        }
                        "Light snow","Moderate snow","Heavy snow"->{lottieAnimationView.setAnimation(R.raw.snow)
                            lottieAnimationView.playAnimation()
                        }

                    }





                    // forecast buttons setup

                    todayforecast.setOnClickListener {
                        todayforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.mygrey))
                        tomorrowforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                        dftforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                        list_index = 0
                        fetchweatherdata()

                    }
                    tomorrowforecast.setOnClickListener {
                        tomorrowforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.mygrey))
                        todayforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                        dftforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))

                        list_index = 1
                        fetchweatherdata()
                    }

                    dftforecast.setOnClickListener{
                        todayforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                        tomorrowforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.white))
                        dftforecast.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.mygrey))
                        list_index=2
                        fetchweatherdata()
                    }


                    // mini box setup

                    val nextHour = currentHour
                    val currentforecast =
                        responseBody?.forecast?.forecastday?.get(list_index)?.hour?.get((nextHour) % 24)?.temp_c
                    val currentplusone =
                        responseBody?.forecast?.forecastday?.get(list_index)?.hour?.get((nextHour + 1) % 24)?.temp_c
                    val currentplustwo =
                        responseBody?.forecast?.forecastday?.get(list_index)?.hour?.get((nextHour + 2) % 24)?.temp_c
                    val currentplusthree =
                        responseBody?.forecast?.forecastday?.get(list_index)?.hour?.get((nextHour + 3) % 24)?.temp_c


                    // mini boxes initialization

                    miniboxcurrenttime.text = "${nextHour}:00"
                    miniboxcurrenttemp.text = "$currentforecast °C"

                    miniboxcurrentplusone.text = "${((nextHour + 1) % 24)}:00"
                    miniboxplusonetemp.text = "$currentplusone °C"

                    miniboxcurrentplustwo.text = "${((nextHour + 2) % 24)}:00"
                    miniboxplustwotemp.text = "$currentplustwo °C"

                    miniboxcurrentplusthree.text = "${((nextHour + 3) % 24)}:00"
                    miniboxplusthreetemp.text = "$currentplusthree °C"




                    tempcurrent.text = "$temprature °C"
                    weatherinfo.text = overview
                    wind_speed.text = "$windspeed km/h"
                    humidity.text = "$humiditycurremt%"
                    chance_of_rain.text = "${chanceofrain.toString()}%"
                    Log.e("check", "onResponse: $responseBody",)

                } else {
                    Log.e(
                        "WeatherAPI",
                        "Error Code: ${response.code()}, Message: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<weatherdata>, t: Throwable) {
                Log.e("WeatherAPI", "Failed to call API: ${t.message}")
            }
        })
    }
}

//https://api.weatherapi.com/v1/forecast.json?key=&q=Kolkata&days=1&aqi=no&alerts=no