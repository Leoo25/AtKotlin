package com.example.filmesseriess

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


    class FilmeAdapter(private val filmes: List<Filme>) :
        RecyclerView.Adapter<FilmeAdapter.ViewHolder>() {

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            val nome = view.findViewById<TextView>(R.id.text_nome)
            val genero = view.findViewById<TextView>(R.id.text_genero)
            val assistido = view.findViewById<CheckBox>(R.id.check_assistido)
            val nota = view.findViewById<EditText>(R.id.edit_nota)

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_filme, parent, false)

            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return filmes.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val filme = filmes[position]

            holder.nome.text = filme.nome
            holder.genero.text = filme.genero

            holder.assistido.isChecked = filme.assistido

            holder.assistido.setOnCheckedChangeListener { _, isChecked ->

                filme.assistido = isChecked

                if (isChecked) {

                    val notaTexto = holder.nota.text.toString()

                    if (notaTexto.isNotEmpty()) {
                        filme.nota = notaTexto.toDouble()
                    }

                }

            }

        }
    }
