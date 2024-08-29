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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configurar el Spinner con las categorías
        val categories = arrayOf("Todos", "Playas", "Montañas", "Ciudades Históricas", "Maravillas del Mundo", "Selvas")
        val spinner: Spinner = findViewById(R.id.spinner_destinations)

        //Creacion Adapter
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this

        // Configurar el botón "Explorar Destinos"
        val exploreButton: Button = findViewById(R.id.btn_explore_destinations)
        exploreButton.setOnClickListener {
            val intent = Intent(this, DestinationsActivity::class.java).apply {
                putExtra("CATEGORY", selectedCategory)
            }
            startActivity(intent)
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        selectedCategory = parent?.getItemAtPosition(position).toString()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // No se necesita implementar nada aquí
    }
}
