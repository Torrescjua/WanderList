package com.example.wanderlist.logica

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.wanderlist.R

class RecommendationsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendations)

        val recommendedActivityTextView: TextView = findViewById(R.id.tv_recommended_activity)
        recommendedActivityTextView.text = getRecommendedActivity()
    }

    private fun getRecommendedActivity(): String {
        val favoritesList = MainActivity.favoritesList

        if (favoritesList.isEmpty()) {
            return "NA"
        }

        val mostFrequentCategory = favoritesList
            .groupingBy { it.categoria }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key

        val filteredFavorites = favoritesList.filter { it.categoria == mostFrequentCategory }

        if (filteredFavorites.isEmpty()) {
            return "NA"
        }

        val randomDestination = filteredFavorites.random()

        return "${randomDestination.nombre} - ${randomDestination.plan}"
    }
}
