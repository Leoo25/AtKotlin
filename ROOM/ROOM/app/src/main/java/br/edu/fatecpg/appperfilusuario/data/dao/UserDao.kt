package br.edu.fatecpg.appperfilusuario.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.edu.fatecpg.appperfilusuario.model.User

/**
 * DAO (Data Access Object) - Define as operações no banco.
 * Todas as funções suspend rodam fora da thread principal.
 */
@Dao
interface UserDao {

    // CREATE - retorna o id gerado
    @Insert
    suspend fun insert(user: User): Long

    // READ - busca por email/senha (login)
    @Query("SELECT * FROM users WHERE email = :email AND senha = :senha LIMIT 1")
    suspend fun login(email: String, senha: String): User?

    // READ - busca usuário por id
    @Query("SELECT * FROM users WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): User?

    // READ - verifica se email já está cadastrado
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun countByEmail(email: String): Int

    // UPDATE
    @Update
    suspend fun update(user: User): Int

    // DELETE
    @Delete
    suspend fun delete(user: User): Int
}
