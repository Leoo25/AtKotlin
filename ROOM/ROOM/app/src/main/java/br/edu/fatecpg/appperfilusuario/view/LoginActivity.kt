package br.edu.fatecpg.appperfilusuario.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.appperfilusuario.databinding.ActivityLoginBinding
import br.edu.fatecpg.appperfilusuario.utils.SessionManager
import br.edu.fatecpg.appperfilusuario.utils.Validator
import br.edu.fatecpg.appperfilusuario.viewmodel.Resultado
import br.edu.fatecpg.appperfilusuario.viewmodel.UserViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var session: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        // Se já estiver logado, vai direto pra tela de perfil
        if (session.estaLogado()) {
            irParaPerfil()
            return
        }

        configurarObservers()
        configurarListeners()
    }

    private fun configurarObservers() {
        viewModel.resultadoLogin.observe(this) { resultado ->
            mostrarLoading(false)
            when (resultado) {
                is Resultado.Sucesso -> {
                    session.salvarUsuarioLogado(resultado.dado.id)
                    Toast.makeText(this, "Bem-vindo, ${resultado.dado.nome}!", Toast.LENGTH_SHORT).show()
                    irParaPerfil()
                }
                is Resultado.Erro -> {
                    Toast.makeText(this, resultado.mensagem, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun configurarListeners() {
        binding.btnLogin.setOnClickListener { realizarLogin() }
        binding.tvCadastrar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun realizarLogin() {
        val email = binding.etEmail.text.toString().trim()
        val senha = binding.etSenha.text.toString()

        binding.tilEmail.error = null
        binding.tilSenha.error = null

        if (!Validator.emailValido(email)) {
            binding.tilEmail.error = "Email inválido"
            return
        }
        if (senha.isEmpty()) {
            binding.tilSenha.error = "Digite sua senha"
            return
        }

        mostrarLoading(true)
        viewModel.login(email, senha)
    }

    private fun mostrarLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !loading
    }

    private fun irParaPerfil() {
        startActivity(Intent(this, ProfileActivity::class.java))
        finish()
    }
}
