package br.edu.fatecpg.telalogin

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.btn_enviar)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtNome = findViewById<EditText>(R.id.edt_nome)
        val edtIdade = findViewById<EditText>(R.id.edt_idade)
        val btnEnviar = findViewById<Button>(R.id.btn_enviar)
        val txtResultado = findViewById<TextView>(R.id.txv_resultado)

        btnEnviar.setOnClickListener {
            val nome = edtNome.text.toString()
            val idade = edtIdade.text.toString()

            if (nome.isNotEmpty() && idade.isNotEmpty()){
                txtResultado.text = "Bem-vindo, $nome! Você tem $idade anos."
            }
            else{
                txtResultado.text = "Preencha seus dados!"
            }
        }

    }
}