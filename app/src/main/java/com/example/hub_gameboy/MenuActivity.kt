package com.example.hub_gameboy

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity

class MenuActivity : ComponentActivity() {
    private val aplicativos = listOf("Placar", "Calculadora", "Em Breve")
    private var indexAtual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnSetaEsquerda: Button = findViewById(R.id.btn_seta_esquerda)
        val btnSetaDireita: Button = findViewById(R.id.btn_seta_direita)
        val btnIcone: Button = findViewById(R.id.icon_app_menu)

        atualizarIcone(btnIcone)

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
                "Em Breve" -> {
                    // Opcional: mostrar um aviso
                }
            }
        }
    }

    private fun atualizarIcone(botao: Button) {
        when (aplicativos[indexAtual]) {
            "Placar" -> botao.text = "0 : 0\n\nPlacar"
            "Calculadora" -> botao.text = "Calculadora"
            "Em Breve" -> botao.text = "?\n\nEm Breve"
        }
    }
}