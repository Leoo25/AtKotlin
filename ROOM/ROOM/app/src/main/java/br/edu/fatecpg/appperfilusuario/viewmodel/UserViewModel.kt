package br.edu.fatecpg.appperfilusuario.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.edu.fatecpg.appperfilusuario.data.database.AppDatabase
import br.edu.fatecpg.appperfilusuario.model.User
import br.edu.fatecpg.appperfilusuario.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * ViewModel responsável pelas operações de usuário.
 * Expõe LiveData para a View observar mudanças de estado.
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    // Estados expostos via LiveData
    private val _resultadoCadastro = MutableLiveData<Resultado<Long>>()
    val resultadoCadastro: LiveData<Resultado<Long>> = _resultadoCadastro

    private val _resultadoLogin = MutableLiveData<Resultado<User>>()
    val resultadoLogin: LiveData<Resultado<User>> = _resultadoLogin

    private val _usuarioAtual = MutableLiveData<User?>()
    val usuarioAtual: LiveData<User?> = _usuarioAtual

    private val _resultadoAtualizacao = MutableLiveData<Resultado<Boolean>>()
    val resultadoAtualizacao: LiveData<Resultado<Boolean>> = _resultadoAtualizacao

    private val _resultadoDelete = MutableLiveData<Resultado<Boolean>>()
    val resultadoDelete: LiveData<Resultado<Boolean>> = _resultadoDelete

    init {
        val dao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(dao)
    }

    fun cadastrar(user: User) {
        viewModelScope.launch {
            try {
                if (repository.emailJaCadastrado(user.email)) {
                    _resultadoCadastro.value = Resultado.Erro("Este email já está cadastrado")
                    return@launch
                }
                val id = repository.cadastrar(user)
                _resultadoCadastro.value = Resultado.Sucesso(id)
            } catch (e: Exception) {
                _resultadoCadastro.value = Resultado.Erro(e.message ?: "Erro ao cadastrar")
            }
        }
    }

    fun login(email: String, senha: String) {
        viewModelScope.launch {
            try {
                val user = repository.login(email, senha)
                if (user != null) {
                    _resultadoLogin.value = Resultado.Sucesso(user)
                } else {
                    _resultadoLogin.value = Resultado.Erro("Email ou senha inválidos")
                }
            } catch (e: Exception) {
                _resultadoLogin.value = Resultado.Erro(e.message ?: "Erro no login")
            }
        }
    }

    fun carregarUsuario(id: Long) {
        viewModelScope.launch {
            try {
                _usuarioAtual.value = repository.buscarPorId(id)
            } catch (e: Exception) {
                _usuarioAtual.value = null
            }
        }
    }

    fun atualizar(user: User) {
        viewModelScope.launch {
            try {
                val rows = repository.atualizar(user)
                if (rows > 0) {
                    _usuarioAtual.value = user
                    _resultadoAtualizacao.value = Resultado.Sucesso(true)
                } else {
                    _resultadoAtualizacao.value = Resultado.Erro("Nenhuma alteração realizada")
                }
            } catch (e: Exception) {
                _resultadoAtualizacao.value = Resultado.Erro(e.message ?: "Erro ao atualizar")
            }
        }
    }

    fun deletar(user: User) {
        viewModelScope.launch {
            try {
                val rows = repository.deletar(user)
                if (rows > 0) {
                    _resultadoDelete.value = Resultado.Sucesso(true)
                } else {
                    _resultadoDelete.value = Resultado.Erro("Falha ao deletar")
                }
            } catch (e: Exception) {
                _resultadoDelete.value = Resultado.Erro(e.message ?: "Erro ao deletar")
            }
        }
    }
}

/**
 * Wrapper para representar resultado de operações.
 */
sealed class Resultado<out T> {
    data class Sucesso<T>(val dado: T) : Resultado<T>()
    data class Erro(val mensagem: String) : Resultado<Nothing>()
}
