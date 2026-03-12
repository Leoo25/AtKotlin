package br.edu.fatecpg.leideohm

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtTensao = findViewById<EditText>(R.id.edt_tensao)
        val edtResistencia = findViewById<EditText>(R.id.edt_resistencia)
        val edtCorrente = findViewById<EditText>(R.id.edt_corrente)
        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val txtResultado = findViewById<TextView>(R.id.txv_resultado)

        btnCalcular.setOnClickListener {
            val tensao = edtTensao.text.toString().toDoubleOrNull()
            val resistencia = edtResistencia.text.toString().toDoubleOrNull()
            val corrente = edtCorrente.text.toString().toDoubleOrNull()

            when{
                tensao != null && resistencia != null -> {
                    val i = tensao / resistencia
                    txtResultado.text = "Corrente: %.2f A".format(i)
                }

                tensao != null && corrente != null -> {
                    val r = tensao / corrente
                    txtResultado.text = "Resistência: %.2f F".format(r)
                }

                resistencia != null && corrente != null -> {
                    val v = resistencia * corrente
                    txtResultado.text = "Tensão: %.2f V".format(v)
                }

                else ->{
                    txtResultado.text = "Preencha pelo menos dois valores!"
                }
            }
        }
    }
}