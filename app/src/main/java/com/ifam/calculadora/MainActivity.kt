package com.ifam.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var expressaoTextView: TextView
    private lateinit var resultadoTextView: TextView
    private var currentExpression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        expressaoTextView = findViewById(R.id.text_view_expressao)
        resultadoTextView = findViewById(R.id.text_view_resultado)

        val buttonIds = listOf(
            R.id.btn_zero, R.id.btn_um, R.id.btn_dois, R.id.btn_tres, R.id.btn_quatro,
            R.id.btn_cinco, R.id.btn_seis, R.id.btn_sete, R.id.btn_oito, R.id.btn_nove,
            R.id.btn_soma, R.id.btn_sub, R.id.btn_mult, R.id.btn_div, R.id.btn_ponto
        )

        for (buttonId in buttonIds) {
            val button = findViewById<Button>(buttonId)
            button.setOnClickListener { onButtonClick(button.text.toString()) }
        }

        val btnDel = findViewById<Button>(R.id.btn_del)
        btnDel.setOnClickListener { onDeleteButtonClick() }

        val btnIgual = findViewById<Button>(R.id.btn_igual)
        btnIgual.setOnClickListener { calculateResult() }
    }

    private fun onButtonClick(value: String) {
        val currentExpression = expressaoTextView.text.toString()
        expressaoTextView.text = "$currentExpression$value"
    }

    private fun onDeleteButtonClick() {
        val currentExpression = expressaoTextView.text.toString()
        if (currentExpression.isNotEmpty()) {
            val newExpression = currentExpression.substring(0, currentExpression.length - 1)
            expressaoTextView.text = newExpression
        }
    }

    private fun calculateResult() {
        if (currentExpression.isNotEmpty()) {
            try {
                val result = evalExpression(currentExpression)
                resultadoTextView.text = result.toString()
            } catch (e: Exception) {
                resultadoTextView.text = "Erro"
            }
        }
    }

    private fun evalExpression(expression: String): Double {
        return 0.0
    }

}