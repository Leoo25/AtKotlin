package com.example.filmesseriess

interface FilmeDao {
    fun adicionarFilme(filme: Filme)

    fun obterFilmes(): List<Filme>

}