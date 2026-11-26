package com.example.ejerciciointents

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejerciciointents.databinding.ActivityDetalleBinding

class DetalleActivity : BaseActivity() {

    private lateinit var binding: ActivityDetalleBinding

    companion object {
        // Constante para la clave del extra (evita errores de escritura entre pantallas)
        const val EXTRA_NOMBRE = "extra_nombre"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Aquí SÍ mostramos la flecha 'Arriba' (<-) porque venimos de MainActivity
        configurarToolbar(binding.toolbar, mostrarArriba = true)

        // Recuperamos el dato enviado desde MainActivity a través de la propiedad 'intent'
        val nombreRecibido = intent.getStringExtra(EXTRA_NOMBRE) ?: "Sin nombre"
        binding.tvRecibido.text = "Hola, $nombreRecibido"
    }
}