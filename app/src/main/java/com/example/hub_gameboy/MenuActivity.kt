package com.example.hub_gameboy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity

class MenuActivity : AppCompatActivity() {
    private val aplicativos = listOf("Placar", "Calculadora", "Jogo da Velha")
    private var indexAtual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnSetaEsquerda: Button = findViewById(R.id.btn_seta_esquerda)
        val btnSetaDireita: Button = findViewById(R.id.btn_seta_direita)
        val btnIcone: Button = findViewById(R.id.icon_app_menu)
        val btnTema: Button = findViewById(R.id.btnTema)

        atualizarIcone(btnIcone)

        btnTema.setOnClickListener {
            val isNightTheme = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
            when (isNightTheme) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                Configuration.UI_MODE_NIGHT_NO -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
            }
        }

        btnSetaDireita.setOnClickListener {
            indexAtual++
            if (indexAtual >= aplicativos.size) {
                indexAtual = 0
            }
            atualizarIcone(btnIcone)
        }

        btnSetaEsquerda.setOnClickListener {
            indexAtual--
            if (indexAtual < 0) {
                indexAtual = aplicativos.size - 1
            }
            atualizarIcone(btnIcone)
        }

        btnIcone.setOnClickListener {
            when (aplicativos[indexAtual]) {
                "Placar" -> {
                    val intent = Intent(this, PlacarActivity::class.java)
                    startActivity(intent)
                }

                "Calculadora" -> {
                    val intent = Intent(this, CalculadoraActivity::class.java)
                    startActivity(intent)
                }

                "Jogo da Velha" -> {
                    val intent = Intent(this, TicTacToeActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }

    private fun atualizarIcone(botao: Button) {
        when (aplicativos[indexAtual]) {
            "Placar" -> botao.text = "0 : 0\n\nPlacar"
            "Calculadora" -> botao.text = "Calculadora"
            "Jogo da Velha" -> botao.text = "X O X\n\nJogo da\nVelha"
        }
    }
}