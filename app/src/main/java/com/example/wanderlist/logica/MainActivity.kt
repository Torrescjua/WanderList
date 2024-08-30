package com.example.wanderlist.logica

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var selectedCategory: String

    companion object {
        val favoritesList = mutableListOf<Destino>()  // Lista para almacenar los destinos favoritos
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupSpinner()
        setupExploreButton()
        setupFavoritesButton()
        setupRecommendations()
    }

    private fun setupSpinner() {
        findViewById<Spinner>(R.id.spinner_destinations).apply {
<<<<<<< HEAD
            adapter = ArrayAdapter(
                this@MainActivity, android.R.layout.simple_spinner_item,
                arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
            ).also {
                it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
=======
            adapter = ArrayAdapter.createFromResource(
                this@MainActivity,
                R.array.destination_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
>>>>>>> fe16f41be260a3a1e6fbf7ced42d22b8a83d8ae2
            }
            onItemSelectedListener = this@MainActivity
        }
    }

        private fun setupExploreButton() {
            findViewById<Button>(R.id.btn_explore_destinations).setOnClickListener {
                startActivity(
                    Intent(this, DestinationsActivity::class.java).putExtra(
                        "CATEGORY",
                        selectedCategory
                    )
                )
            }
        }

        private fun setupFavoritesButton() {
            findViewById<Button>(R.id.btn_favorites).setOnClickListener {
                startActivity(Intent(this, FavoritesActivity::class.java))
            }
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // No se requiere acción cuando no se selecciona nada
    }
}
