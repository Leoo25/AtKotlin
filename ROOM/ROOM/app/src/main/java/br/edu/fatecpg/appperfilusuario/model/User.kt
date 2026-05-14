package br.edu.fatecpg.appperfilusuario.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidade User mapeada para tabela "users" no banco Room.
 * Representa o perfil do usuário com todos os dados pessoais.
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val nome: String,
    val email: String,
    val senha: String,
    val whatsapp: String = "",
    val endereco: String = ""
)
