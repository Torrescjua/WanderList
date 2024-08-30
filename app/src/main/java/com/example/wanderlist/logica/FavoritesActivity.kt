package com.example.wanderlist.logica

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R

class FavoritesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        setupListView()
    }

    private fun setupListView() {
        val listView: ListView = findViewById(R.id.listViewFavoritos)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, MainActivity.favoritesList.map { it.nombre })
        listView.adapter = adapter
    }
}

