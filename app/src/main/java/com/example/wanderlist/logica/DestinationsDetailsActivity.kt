package com.example.wanderlist.logica

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R
import org.json.JSONObject

class DestinationsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations_details)

        val destinoString = intent.getStringExtra("DESTINO")
        val destino = JSONObject(destinoString)

        val textViewDetails: TextView = findViewById(R.id.textViewDetails)
        val btnAddToFavorites: Button = findViewById(R.id.btnAddToFavorites)

        // Mostrar detalles del destino
        val details = """
            Nombre: ${destino.getString("nombre")}
            País: ${destino.getString("pais")}
            Categoría: ${destino.getString("categoria")}
            Plan: ${destino.getString("plan")}
            Precio: ${destino.getInt("precio")} USD
        """.trimIndent()

        textViewDetails.text = details

        // Configurar el botón para añadir a favoritos
        btnAddToFavorites.setOnClickListener {
            btnAddToFavorites.isEnabled = false
            Toast.makeText(this, "Añadido a favoritos", Toast.LENGTH_SHORT).show()
            // Aquí podrías guardar el destino en una lista de favoritos en un companion object o base de datos
        }
    }
}
