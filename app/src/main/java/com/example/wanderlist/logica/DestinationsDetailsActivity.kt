package com.example.wanderlist.logica

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R
import org.json.JSONObject

class DestinationsDetailsActivity : AppCompatActivity() {

    private lateinit var destino: Destino

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations_details)

        val destinoString = intent.getStringExtra("DESTINO")
        destino = parseDestinoFromJson(destinoString)

        displayDestinoDetails()
        setupAddToFavoritesButton()
    }

    private fun parseDestinoFromJson(destinoString: String?): Destino {
        val destinoJson = JSONObject(destinoString)
        return Destino(
            id = destinoJson.getInt("id"),
            nombre = destinoJson.getString("nombre"),
            pais = destinoJson.getString("pais"),
            categoria = destinoJson.getString("categoria"),
            plan = destinoJson.getString("plan"),
            precio = destinoJson.getInt("precio")
        )
    }

    private fun displayDestinoDetails() {
        val textViewDetails: TextView = findViewById(R.id.textViewDetails)
        val details = """
            Nombre: ${destino.nombre}
            País: ${destino.pais}
            Categoría: ${destino.categoria}
            Plan: ${destino.plan}
            Precio: ${destino.precio} USD
        """.trimIndent()
        textViewDetails.text = details
    }

    private fun setupAddToFavoritesButton() {
        val btnAddToFavorites: Button = findViewById(R.id.btnAddToFavorites)
        btnAddToFavorites.setOnClickListener {
            if (addToFavorites()) {
                btnAddToFavorites.isEnabled = false
                Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "El destino ya está en favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addToFavorites(): Boolean {
        return if (!MainActivity.favoritesList.contains(destino)) {
            MainActivity.favoritesList.add(destino)
            true
        } else {
            false
        }
    }
}
