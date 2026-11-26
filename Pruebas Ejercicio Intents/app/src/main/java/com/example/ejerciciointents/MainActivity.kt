package com.example.ejerciciointents

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejerciciointents.databinding.ActivityMainBinding

// Heredamos de BaseActivity en lugar de AppCompatActivity
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    // Ocultamos la opción "Inicio" del menú porque ya estamos en Inicio
    override val opcionMenuActual = R.id.action_inicio

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Registramos la toolbar SIN flecha atrás (porque esta es la pantalla principal)
        configurarToolbar(binding.toolbar, mostrarArriba = false)

        // Al pulsar el botón, saltamos a DetalleActivity enviando el texto escrito
        binding.btnEnviar.setOnClickListener {
            val nombre = binding.etNombre.text.toString().ifBlank { "Invitado" }

            // INTENT EXPLÍCITO CON EXTRAS (clave - valor)
            val intent = Intent(this, DetalleActivity::class.java).apply {
                putExtra(DetalleActivity.EXTRA_NOMBRE, nombre)
            }
            startActivity(intent)
        }
    }
}