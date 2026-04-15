package com.example.hub_gameboy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TicTacToeActivity : AppCompatActivity() {

    private lateinit var tvTurno: TextView
    private val botoes = Array(3) { arrayOfNulls<Button>(3) }
    private var jogadorAtual = "X"
    private var jogoAtivo = true
    private var jogadas = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_tictactoe)

        tvTurno = findViewById(R.id.tvTurno)

        botoes[0][0] = findViewById(R.id.btn00)
        botoes[0][1] = findViewById(R.id.btn01)
        botoes[0][2] = findViewById(R.id.btn02)
        botoes[1][0] = findViewById(R.id.btn10)
        botoes[1][1] = findViewById(R.id.btn11)
        botoes[1][2] = findViewById(R.id.btn12)
        botoes[2][0] = findViewById(R.id.btn20)
        botoes[2][1] = findViewById(R.id.btn21)
        botoes[2][2] = findViewById(R.id.btn22)

        for (i in 0..2) {
            for (j in 0..2) {
                botoes[i][j]?.setOnClickListener { onBotaoClicado(botoes[i][j]!!) }
            }
        }

        findViewById<Button>(R.id.btnReiniciar).setOnClickListener { reiniciarJogo() }
        findViewById<Button>(R.id.btnVoltarMenu).setOnClickListener { finish() }
    }

    private fun onBotaoClicado(botao: Button) {
        if (!jogoAtivo || botao.text.isNotEmpty()) return

        botao.text = jogadorAtual
        jogadas++

        if (verificarVitoria()) {
            tvTurno.text = getString(R.string.venceu, jogadorAtual)
            jogoAtivo = false
        } else if (jogadas == 9) {
            tvTurno.text = getString(R.string.deu_velha)
            jogoAtivo = false
        } else {
            jogadorAtual = if (jogadorAtual == "X") "O" else "X"
            tvTurno.text = getString(R.string.vez_do, jogadorAtual)
        }
    }

    private fun verificarVitoria(): Boolean {
        val b = Array(3) { i -> Array(3) { j -> botoes[i][j]?.text.toString() } }

        for (i in 0..2) {
            // Checa Linhas
            if (b[i][0].isNotEmpty() && b[i][0] == b[i][1] && b[i][1] == b[i][2]) return true
            // Checa Colunas
            if (b[0][i].isNotEmpty() && b[0][i] == b[1][i] && b[1][i] == b[2][i]) return true
        }

        // Checa as duas Diagonais
        if (b[0][0].isNotEmpty() && b[0][0] == b[1][1] && b[1][1] == b[2][2]) return true
        if (b[0][2].isNotEmpty() && b[0][2] == b[1][1] && b[1][1] == b[2][0]) return true

        return false
    }

    private fun reiniciarJogo() {
        jogadorAtual = "X"
        jogoAtivo = true
        jogadas = 0
        tvTurno.text = getString(R.string.vez_do, jogadorAtual)

        for (i in 0..2) {
            for (j in 0..2) {
                botoes[i][j]?.text = ""
            }
        }
    }
}