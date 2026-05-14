package br.edu.fatecpg.appperfilusuario.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Gerencia a sessão do usuário logado via SharedPreferences.
 */
class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun salvarUsuarioLogado(userId: Long) {
        prefs.edit().putLong(KEY_USER_ID, userId).apply()
    }

    fun getUsuarioLogadoId(): Long = prefs.getLong(KEY_USER_ID, -1L)

    fun estaLogado(): Boolean = getUsuarioLogadoId() != -1L

    fun logout() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val PREFS_NAME = "app_perfil_usuario_prefs"
        private const val KEY_USER_ID = "user_id"
    }
}
