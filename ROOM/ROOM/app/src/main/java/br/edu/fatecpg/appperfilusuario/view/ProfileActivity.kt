package br.edu.fatecpg.appperfilusuario.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.edu.fatecpg.appperfilusuario.databinding.ActivityProfileBinding
import br.edu.fatecpg.appperfilusuario.model.User
import br.edu.fatecpg.appperfilusuario.utils.SessionManager
import br.edu.fatecpg.appperfilusuario.viewmodel.Resultado
import br.edu.fatecpg.appperfilusuario.viewmodel.UserViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private val viewModel: UserViewModel by viewModels()
    private lateinit var session: SessionManager
    private var usuarioCarregado: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        session = SessionManager(this)

        configurarObservers()
        configurarListeners()
    }

    override fun onResume() {
        super.onResume()
        // Recarrega o usuário ao voltar da tela de edição
        carregarUsuario()
    }

    private fun carregarUsuario() {
        val userId = session.getUsuarioLogadoId()
        if (userId == -1L) {
            voltarParaLogin()
            return
        }
        viewModel.carregarUsuario(userId)
    }

    private fun configurarObservers() {
        viewModel.usuarioAtual.observe(this) { user ->
            if (user != null) {
                usuarioCarregado = user
                preencherCampos(user)
            } else {
                Toast.makeText(this, "Usuário não encontrado", Toast.LENGTH_SHORT).show()
                voltarParaLogin()
            }
        }

        viewModel.resultadoDelete.observe(this) { resultado ->
            when (resultado) {
                is Resultado.Sucesso -> {
                    Toast.makeText(this, "Conta excluída com sucesso", Toast.LENGTH_LONG).show()
                    session.logout()
                    voltarParaLogin()
                }
                is Resultado.Erro -> {
                    Toast.makeText(this, resultado.mensagem, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun preencherCampos(user: User) {
        binding.tvNome.text = user.nome
        binding.tvEmail.text = user.email
        binding.tvWhatsapp.text = if (user.whatsapp.isNotBlank()) user.whatsapp else "Não informado"
        binding.tvEndereco.text = if (user.endereco.isNotBlank()) user.endereco else "Não informado"

        // Inicial do nome no avatar
        binding.tvInicial.text = user.nome.firstOrNull()?.uppercase() ?: "?"
    }

    private fun configurarListeners() {
        binding.btnEditar.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        binding.btnDeletar.setOnClickListener { confirmarExclusao() }

        binding.btnSair.setOnClickListener { confirmarLogout() }
    }

    private fun confirmarLogout() {
        AlertDialog.Builder(this)
            .setTitle("Sair")
            .setMessage("Deseja realmente sair da conta?")
            .setPositiveButton("Sim") { _, _ ->
                session.logout()
                voltarParaLogin()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun confirmarExclusao() {
        AlertDialog.Builder(this)
            .setTitle("Excluir conta")
            .setMessage("Esta ação é irreversível. Deseja realmente excluir sua conta?")
            .setPositiveButton("Sim, excluir") { _, _ ->
                usuarioCarregado?.let { viewModel.deletar(it) }
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun voltarParaLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
