package com.example.wanderlist.logica

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var selectedCategory: String

    companion object {
        val favoritesList = mutableListOf<Destino>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categories = arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
        val spinner: Spinner = findViewById(R.id.spinner_destinations)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        val exploreButton: Button = findViewById(R.id.btn_explore_destinations)
        exploreButton.setOnClickListener {
            val intent = Intent(this, DestinationsActivity::class.java).apply {
                putExtra("CATEGORY", selectedCategory)
            }
            startActivity(intent)
        }

        val favoritesButton: Button = findViewById(R.id.btn_favorites)
        favoritesButton.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        val recommendationsButton: Button = findViewById(R.id.btn_recommendations)
        recommendationsButton.setOnClickListener {
            val intent = Intent(this, RecommendationsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // No es necesario manejar nada aquí
    }
}