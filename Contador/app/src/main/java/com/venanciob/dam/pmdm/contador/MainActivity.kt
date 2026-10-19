package com.venanciob.dam.pmdm.contador

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.venanciob.dam.pmdm.contador.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {

    // Clase generada por View Binding a partir de activity_main.xml
    private lateinit var binding: ActivityMainBinding

    // Estado de la pantalla: el valor actual del contador
    private var contador = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inflar el layout con View Binding en lugar de setContentView(R.layout...)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Código de la plantilla, adaptado a View Binding (binding.root es la vista "main")
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mostrar el valor inicial
        mostrarContador()

        // Eventos: qué hacer cuando se pulsa cada botón
        binding.btnSumar.setOnClickListener {
            contador ++
            mostrarContador()
        }

        binding.btnReiniciar.setOnClickListener {
            contador = 0
            mostrarContador()
        }
    }

    // Un único sitio que actualiza la interfaz a partir del estado
    private fun mostrarContador() {
        binding.tvContador.text = contador.toString()
    }
}