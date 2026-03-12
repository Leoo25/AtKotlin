package br.edu.fatecpg.calculotemperatura

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val edtCelsius = findViewById<EditText>(R.id.edt_celsius)
        val btnConverter = findViewById<Button>(R.id.btn_converter)
        val txtResultado = findViewById<TextView>(R.id.txv_resultado)

        btnConverter.setOnClickListener {
            val celsius = edtCelsius.text.toString().toDoubleOrNull()

            if (celsius != null){
                val fahrenheit = (celsius * 9 / 5) + 32
                txtResultado.text = "Resultado: %.2f ºF".format(fahrenheit)
            }
            else{
                txtResultado.text = "Digite um valor válido!"
            }
        }
    }
}