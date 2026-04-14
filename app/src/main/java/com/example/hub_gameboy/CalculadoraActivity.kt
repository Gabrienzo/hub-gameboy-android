package com.example.hub_gameboy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CalculadoraActivity : AppCompatActivity() {
    private lateinit var tvDisplay: TextView
    private lateinit var tvHistorico: TextView

    private var currentInput: String = ""
    private var operand: Double? = null
    private var pendingOp: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_calculadora)

        tvDisplay = findViewById(R.id.txtResultado)
        tvHistorico = findViewById(R.id.txtHistorico)

        val digits = listOf(
            "0" to R.id.btn0, "1" to R.id.btn1, "2" to R.id.btn2,
            "3" to R.id.btn3, "4" to R.id.btn4, "5" to R.id.btn5,
            "6" to R.id.btn6, "7" to R.id.btn7, "8" to R.id.btn8,
            "9" to R.id.btn9, "." to R.id.btnPonto
        )
        digits.forEach { (digit, id) ->
            findViewById<Button>(id).setOnClickListener { appendDigit(digit) }
        }

        val ops = listOf(
            "+" to R.id.btnSomar, "-" to R.id.btnSubtrair,
            "×" to R.id.btnMultiplicar, "÷" to R.id.btnDividir
        )

        val scientificOps = listOf(
            "√" to R.id.btnRaiz,
            "sin" to R.id.btnSin,
            "cos" to R.id.btnCos,
            "tan" to R.id.btnTan
        )

        scientificOps.forEach { (op, id) ->
            findViewById<Button>(id)?.setOnClickListener {
                calcularCientifico(op)
            }
        }

        val bVoltar: Button = findViewById(R.id.btnVoltarMenu)

        bVoltar.setOnClickListener {
            finish()
        }

        ops.forEach { (op, id) ->
            findViewById<Button>(id).setOnClickListener { onOperator(op) }
        }

        findViewById<Button>(R.id.btnIgual).setOnClickListener { onEquals() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearAll() }
        findViewById<Button>(R.id.btnBackspace).setOnClickListener { backspace() }

        updateDisplay()
    }

    private fun appendDigit(d: String) {
        if (d == "." && currentInput.contains(".")) return
        currentInput = if (currentInput == "0") d else currentInput + d
        updateDisplay()
    }

    private fun onOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull()
            if (value != null) {
                if (operand == null)
                    operand = value
                else
                    operand = performOperation(operand!!, value, pendingOp)
            }
            currentInput = ""
        }
        pendingOp = op
        updateDisplay()
    }

    private fun onEquals() {
        if (operand != null && currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull() ?: return
            val result = performOperation(operand!!, value, pendingOp)

            val operacaoCompleta = "${formatNumber(operand!!)} $pendingOp ${formatNumber(value)} ="
            tvHistorico.text = operacaoCompleta

            // Formata o resultado para remover o .0 se for inteiro
            currentInput = formatNumber(result)
            operand = null
            pendingOp = null
            updateDisplay()
        }
    }

    private fun performOperation(a: Double, b: Double, op: String?): Double {
        return when (op) {
            "+" -> a + b
            "-" -> a - b
            "×" -> a * b
            "÷" -> if (b == 0.0) {
                Toast.makeText(this, "Erro: Divisão por 0", Toast.LENGTH_SHORT).show()
                a
            } else a / b

            else -> b
        }
    }

    private fun calcularCientifico(op: String) {
        if (currentInput.isNotEmpty()) {
            val value = currentInput.toDoubleOrNull() ?: return
            val result = when (op) {
                "√" -> kotlin.math.sqrt(value)
                "sin" -> kotlin.math.sin(Math.toRadians(value))
                "cos" -> kotlin.math.cos(Math.toRadians(value))
                "tan" -> kotlin.math.tan(Math.toRadians(value))
                else -> value
            }
            currentInput = formatNumber(result)
            updateDisplay()
        }
    }


    private fun formatNumber(d: Double): String {
        return if (d == d.toLong().toDouble()) d.toLong().toString() else d.toString()
    }

    private fun clearAll() {
        currentInput = ""
        operand = null
        pendingOp = null
        tvHistorico.text = ""
        updateDisplay()
    }

    private fun backspace() {
        if (currentInput.isNotEmpty()) {
            currentInput = currentInput.dropLast(1)
            updateDisplay()
        }
    }

    private fun updateDisplay() {
        val textToShow = StringBuilder()

        // Lógica do Histórico Refatorada
        if (operand != null && pendingOp != null) {
            tvHistorico.text = "${formatNumber(operand!!)} $pendingOp"
        } else if (currentInput.isEmpty()) {
            tvHistorico.text = ""
        }

        // Lógica do Visor Principal
        textToShow.append(currentInput)
        tvDisplay.text = if (textToShow.isEmpty()) "0" else textToShow.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("currentInput", currentInput)
        outState.putDouble("operand", operand ?: Double.NaN)
        outState.putString("pendingOp", pendingOp)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        currentInput = savedInstanceState.getString("currentInput", "")
        val opnd = savedInstanceState.getDouble("operand", Double.NaN)
        operand = if (opnd.isNaN()) null else opnd
        pendingOp = savedInstanceState.getString("pendingOp")
        updateDisplay()
    }
}