package com.example.filmesseriess

class FilmeDaoImp : FilmeDao {
        companion object {
            private val filmes = mutableListOf<Filme>()
        }

        override fun adicionarFilme(filme: Filme) {
            filmes.add(filme)
        }

        override fun obterFilmes(): List<Filme> {
            return filmes
        }
    }
