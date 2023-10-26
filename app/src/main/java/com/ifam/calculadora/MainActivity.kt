package com.ifam.calculadora

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Stack

class MainActivity : AppCompatActivity() {
    private lateinit var expressaoTextView: TextView
    private lateinit var resultadoTextView: TextView

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
        expressaoTextView.text = "$currentExpression $value"
    }

    private fun onDeleteButtonClick() {
        val currentExpression = expressaoTextView.text.toString()
        if (currentExpression.isNotEmpty()) {
            val newExpression = currentExpression.substring(0, currentExpression.length - 1)
            expressaoTextView.text = newExpression
        }
    }

    private fun calculateResult() {
        val currentExpression  = expressaoTextView.text.toString()
        if (currentExpression.isNotEmpty()) {
            val result = evaluateExpression(currentExpression)
            resultadoTextView.text = result.toString()
        }
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.split(" ")
        val operandStack = Stack<Double>()
        val operatorStack = Stack<Char>()

        val precedence = mapOf('+' to 1, '-' to 1, '*' to 2, '/' to 2)

        for (token in tokens) {
            if (token.matches(Regex("\\d+"))) {
                operandStack.push(token.toDouble())
            } else if (token.length == 1 && "+-*/".contains(token)) {
                while (!operatorStack.empty() && precedence[operatorStack.peek()]!! >= precedence[token[0]]!!) {
                    val operator = operatorStack.pop()
                    val operand2 = operandStack.pop()
                    val operand1 = operandStack.pop()
                    val result = performOperation(operand1, operand2, operator)
                    operandStack.push(result)
                }
                operatorStack.push(token[0])
            }
        }

        while (!operatorStack.empty()) {
            val operator = operatorStack.pop()
            val operand2 = operandStack.pop()
            val operand1 = operandStack.pop()
            val result = performOperation(operand1, operand2, operator)
            operandStack.push(result)
        }

        return operandStack.pop()
    }

    private fun performOperation(operand1: Double, operand2: Double, operator: Char): Double {
        return when (operator) {
            '+' -> operand1 + operand2
            '-' -> operand1 - operand2
            '*' -> operand1 * operand2
            '/' -> operand1 / operand2
            else -> throw IllegalArgumentException("Operador desconhecido: $operator")
        }
    }

}