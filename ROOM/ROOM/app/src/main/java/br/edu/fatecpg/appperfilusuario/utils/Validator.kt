package br.edu.fatecpg.appperfilusuario.utils

import android.util.Patterns

object Validator {

    fun emailValido(email: String): Boolean =
        email.isNotBlank() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    fun senhaValida(senha: String): Boolean = senha.length >= 6

    fun nomeValido(nome: String): Boolean = nome.trim().length >= 3

    fun whatsappValido(whatsapp: String): Boolean {
        val apenasDigitos = whatsapp.filter { it.isDigit() }
        return apenasDigitos.length in 10..13
    }
}
