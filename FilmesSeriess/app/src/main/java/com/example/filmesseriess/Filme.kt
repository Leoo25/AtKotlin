package com.example.filmesseriess

data class Filme(
    val nome: String,
    val genero: String,
    var assistido: Boolean = false,
    var nota: Double = 0.0
)
