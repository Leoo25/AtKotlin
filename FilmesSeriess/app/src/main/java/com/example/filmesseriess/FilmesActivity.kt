package com.example.filmesseriess

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FilmesActivity : AppCompatActivity() {

    private val filmeDao = FilmeDaoImp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filmes)

        val recycler = findViewById<RecyclerView>(R.id.recycler_filmes)

        val filmes = filmeDao.obterFilmes()

        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = FilmeAdapter(filmes)
    }
}