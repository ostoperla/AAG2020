package com.trelp.aag2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val movieDetailsBtn = findViewById<Button>(R.id.btn_movie_details)
        movieDetailsBtn.setOnClickListener { startMovieDetailsActivity() }
    }

    private fun startMovieDetailsActivity() {
        val intent = Intent(this, MovieDetailsActivity::class.java)
        startActivity(intent)
    }
}