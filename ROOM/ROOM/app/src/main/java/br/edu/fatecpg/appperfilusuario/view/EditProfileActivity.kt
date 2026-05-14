package br.edu.fatecpg.appperfilusuario.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.appperfilusuario.databinding.ActivityEditProfileBinding
import br.edu.fatecpg.appperfilusuario.model.User
import br.edu.fatecpg.appperfilusuario.utils.SessionManager
import br.edu.fatecpg.appperfilusuario.utils.Validator
import br.edu.fatecpg.appperfilusuario.viewmodel.Resultado
import br.edu.fatecpg.appperfilusuario.viewmodel.UserViewModel

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var session: SessionManager
    private var usuarioOriginal: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)
        binding.toolbar.setNavigationOnClickListener { finish() }

        configurarObservers()
        carregarDados()
        binding.btnSalvar.setOnClickListener { salvarAlteracoes() }
    }

    private fun carregarDados() {
        val userId = session.getUsuarioLogadoId()
        if (userId == -1L) {
            finish()
            return
        }
        viewModel.carregarUsuario(userId)
    }

    private fun configurarObservers() {
        viewModel.usuarioAtual.observe(this) { user ->
            if (user != null) {
                usuarioOriginal = user
                binding.etNome.setText(user.nome)
                binding.etEmail.setText(user.email)
                binding.etWhatsapp.setText(user.whatsapp)
                binding.etEndereco.setText(user.endereco)
            }
        }

        viewModel.resultadoAtualizacao.observe(this) { resultado ->
            mostrarLoading(false)
            when (resultado) {
                is Resultado.Sucesso -> {
                    Toast.makeText(this, "Perfil atualizado!", Toast.LENGTH_SHORT).show()
                    finish()
                }
                is Resultado.Erro -> {
                    Toast.makeText(this, resultado.mensagem, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun salvarAlteracoes() {
        val original = usuarioOriginal ?: return

        val nome = binding.etNome.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val whatsapp = binding.etWhatsapp.text.toString().trim()
        val endereco = binding.etEndereco.text.toString().trim()

        binding.tilNome.error = null
        binding.tilEmail.error = null
        binding.tilWhatsapp.error = null

        if (!Validator.nomeValido(nome)) {
            binding.tilNome.error = "Nome deve ter ao menos 3 caracteres"
            return
        }
        if (!Validator.emailValido(email)) {
            binding.tilEmail.error = "Email inválido"
            return
        }
        if (whatsapp.isNotEmpty() && !Validator.whatsappValido(whatsapp)) {
            binding.tilWhatsapp.error = "WhatsApp inválido"
            return
        }

        val atualizado = original.copy(
            nome = nome,
            email = email,
            whatsapp = whatsapp,
            endereco = endereco
        )

        mostrarLoading(true)
        viewModel.atualizar(atualizado)
    }

    private fun mostrarLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
        binding.btnSalvar.isEnabled = !loading
    }
}
