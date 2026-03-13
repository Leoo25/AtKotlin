package com.example.filmesseriess

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val filmeDao = FilmeDaoImp()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nome = findViewById<EditText>(R.id.edit_nome)
        val genero = findViewById<EditText>(R.id.edit_genero)

        val botaoAdicionar = findViewById<Button>(R.id.btn_adicionar)
        val botaoLista = findViewById<Button>(R.id.btn_lista)

        botaoAdicionar.setOnClickListener {
            val filme = Filme(nome.text.toString(), genero.text.toString())
            Toast.makeText(this, "Filme Cadastrado!", Toast.LENGTH_SHORT).show()
            nome.text.clear()
            genero.text.clear()
            filmeDao.adicionarFilme(filme)
        }

        botaoLista.setOnClickListener {

            val intent = Intent(this, FilmesActivity::class.java)
            startActivity(intent)

        }
    }
}