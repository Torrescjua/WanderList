package com.example.wanderlist.logica

import android.location.Geocoder
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R
import org.json.JSONObject
import okhttp3.*
import java.io.IOException
import java.util.Locale

class DestinationsDetailsActivity : AppCompatActivity() {

    private lateinit var destino: Destino
    private lateinit var btnAddToFavorites: Button
    private lateinit var textViewDetails: TextView
    private lateinit var textViewWeather: TextView

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations_details)

        textViewDetails= findViewById(R.id.textViewDetails)
        textViewWeather= findViewById(R.id.textViewWeather)
        btnAddToFavorites = findViewById(R.id.btnAddToFavorites)

        getDestinationDetails()
        // Configurar el botón para añadir a favoritos
        btnAddToFavorites.setOnClickListener {
            addToFavorites()
        }
    }

    private fun getDestinationDetails() {
        val destinoString = intent.getStringExtra("DESTINO")
        val destinoJson = destinoString?.let { JSONObject(it) }

        // Crear un objeto Destino a partir del JSON recibido
        if (destinoJson != null) {
            destino = Destino(
                id = destinoJson.getInt("id"),
                nombre = destinoJson.getString("nombre"),
                pais = destinoJson.getString("pais"),
                categoria = destinoJson.getString("categoria"),
                plan = destinoJson.getString("plan"),
                precio = destinoJson.getInt("precio")
            )
        }
        showDetails(destino)
    }

    private fun showDetails(destino: Destino) {

        val details = """
        <b>${destino.nombre}</b><br>
        ${destino.pais}<br>
        ${destino.categoria}<br>
        ${destino.plan}<br>
        USD ${destino.precio}
    """.trimIndent()

        getWeather(destino.nombre)
        textViewDetails.text = android.text.Html.fromHtml(details, android.text.Html.FROM_HTML_MODE_COMPACT)

    }

    private fun addToFavorites() {
        // Verificar si el destino ya está en la lista de favoritos
        if (!MainActivity.favoritesList.contains(destino)) {
            MainActivity.favoritesList.add(destino)
            btnAddToFavorites.isEnabled = false
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "El destino ya está en favoritos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getWeather(locationName: String) {
        val geocoder = Geocoder(this, Locale.getDefault())

        // Obtener las coordenadas usando el Geocoder
        val addresses = geocoder.getFromLocationName(locationName, 1)

        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val address = addresses[0]
                val lat = address?.latitude
                val lon = address?.longitude

                // Continuar con la llamada a la API usando lat y lon
                if (lat != null) {
                    if (lon != null) {
                         fetchWeatherData(lat, lon)
                    }
                }
            } else {
                // Manejar el caso donde no se encontraron direcciones
                runOnUiThread {
                    Toast.makeText(this@DestinationsDetailsActivity, "No se encontraron coordenadas para $locationName", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun fetchWeatherData(lat: Double, lon: Double) {
        val apiKey = "38c483b08e1596f2fd4daf70c5b2b59b"
        val url = "https://api.openweathermap.org/data/2.5/forecast?lat=$lat&lon=$lon&appid=$apiKey&units=metric"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Manejar errores
                runOnUiThread {
                    Toast.makeText(this@DestinationsDetailsActivity, "Error al obtener el clima", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {

                response.body?.let { responseBody ->
                    val json = JSONObject(responseBody.string())

                    // Extraer información del clima
                    val weatherArray = json.getJSONArray("list")
                    if (weatherArray.length() > 0) {
                        val weatherData = weatherArray.getJSONObject(0)
                        val main = weatherData.getJSONObject("main")
                        val weather = weatherData.getJSONArray("weather").getJSONObject(0)

                        val temperatura = main.getDouble("temp")
                        val descripcion = weather.getString("description")


                        val clima = """
                        $descripcion
                        ${temperatura}°C
                    """.trimIndent()
                        runOnUiThread {
                            textViewWeather.text = clima
                        }
                    }
                }
            }
        })
    }
}