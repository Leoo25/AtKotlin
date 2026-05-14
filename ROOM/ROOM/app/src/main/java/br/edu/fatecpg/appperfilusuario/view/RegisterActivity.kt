package br.edu.fatecpg.appperfilusuario.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.appperfilusuario.databinding.ActivityRegisterBinding
import br.edu.fatecpg.appperfilusuario.model.User
import br.edu.fatecpg.appperfilusuario.utils.Validator
import br.edu.fatecpg.appperfilusuario.viewmodel.Resultado
import br.edu.fatecpg.appperfilusuario.viewmodel.UserViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        configurarObservers()
        binding.btnCadastrar.setOnClickListener { cadastrar() }
    }

    private fun configurarObservers() {
        viewModel.resultadoCadastro.observe(this) { resultado ->
            mostrarLoading(false)
            when (resultado) {
                is Resultado.Sucesso -> {
                    Toast.makeText(this, "Cadastro realizado! Faça login.", Toast.LENGTH_LONG).show()
                    finish()
                }
                is Resultado.Erro -> {
                    Toast.makeText(this, resultado.mensagem, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun cadastrar() {
        val nome = binding.etNome.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val senha = binding.etSenha.text.toString()
        val whatsapp = binding.etWhatsapp.text.toString().trim()
        val endereco = binding.etEndereco.text.toString().trim()

        // limpa erros
        binding.tilNome.error = null
        binding.tilEmail.error = null
        binding.tilSenha.error = null
        binding.tilWhatsapp.error = null

        if (!Validator.nomeValido(nome)) {
            binding.tilNome.error = "Nome deve ter ao menos 3 caracteres"
            return
        }
        if (!Validator.emailValido(email)) {
            binding.tilEmail.error = "Email inválido"
            return
        }
        if (!Validator.senhaValida(senha)) {
            binding.tilSenha.error = "Senha deve ter ao menos 6 caracteres"
            return
        }
        if (whatsapp.isNotEmpty() && !Validator.whatsappValido(whatsapp)) {
            binding.tilWhatsapp.error = "WhatsApp inválido"
            return
        }

        val user = User(
            nome = nome,
            email = email,
            senha = senha,
            whatsapp = whatsapp,
            endereco = endereco
        )

        mostrarLoading(true)
        viewModel.cadastrar(user)
    }

    private fun mostrarLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnCadastrar.isEnabled = !loading
    }
}
