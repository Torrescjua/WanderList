package com.example.wanderlist.logica

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R
import org.json.JSONArray
import org.json.JSONObject

class DestinationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_destinations)

        val listView: ListView = findViewById(R.id.listViewDestinos)
        val selectedCategory = intent.getStringExtra("CATEGORY") ?: "Todos"

        // Cargar y filtrar destinos del archivo JSON
        val destinos = loadDestinosFromJson(selectedCategory)

        // Configurar el adaptador
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, destinos.map { it.nombre })
        listView.adapter = adapter

        // Listener para abrir detalles de un destino
        listView.setOnItemClickListener { _, _, position, _ ->
            val destino = destinos[position]
            val intent = Intent(this, DestinationsDetailsActivity::class.java).apply {
                putExtra("DESTINO", destino.toJson().toString())
            }
            startActivity(intent)
        }
    }

    private fun loadDestinosFromJson(category: String): List<Destino> {
        val destinos = mutableListOf<Destino>()
        val json = assets.open("destinos.json").bufferedReader().use { it.readText() }
        val jsonArray = JSONObject(json).getJSONArray("destinos")

        for (i in 0 until jsonArray.length()) {
            val destinoJson = jsonArray.getJSONObject(i)
            val destino = Destino.fromJson(destinoJson)

            if (category == "Todos" || destino.categoria == category) {
                destinos.add(destino)
            }
        }

        return destinos
    }
}

// Clase modelo para Destino
data class Destino(
    val id: Int,
    val nombre: String,
    val pais: String,
    val categoria: String,
    val plan: String,
    val precio: Int
) {
    companion object {
        fun fromJson(json: JSONObject): Destino {
            return Destino(
                json.getInt("id"),
                json.getString("nombre"),
                json.getString("pais"),
                json.getString("categoria"),
                json.getString("plan"),
                json.getInt("precio")
            )
        }
    }

    fun toJson(): JSONObject {
        return JSONObject().apply {
            put("id", id)
            put("nombre", nombre)
            put("pais", pais)
            put("categoria", categoria)
            put("plan", plan)
            put("precio", precio)
        }
    }
}
