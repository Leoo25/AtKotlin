package br.edu.fatecpg.appperfilusuario.repository

import br.edu.fatecpg.appperfilusuario.data.dao.UserDao
import br.edu.fatecpg.appperfilusuario.model.User

/**
 * Repository - centraliza acesso aos dados.
 * Abstrai a fonte (Room) do ViewModel.
 */
class UserRepository(private val userDao: UserDao) {

    suspend fun cadastrar(user: User): Long = userDao.insert(user)

    suspend fun login(email: String, senha: String): User? =
        userDao.login(email, senha)

    suspend fun buscarPorId(id: Long): User? = userDao.getById(id)

    suspend fun emailJaCadastrado(email: String): Boolean =
        userDao.countByEmail(email) > 0

    suspend fun atualizar(user: User): Int = userDao.update(user)

    suspend fun deletar(user: User): Int = userDao.delete(user)
}
