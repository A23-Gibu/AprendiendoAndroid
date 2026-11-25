package com.example.ejerciciodialogos

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejerciciodialogos.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // =========================================================================
        // BLOQUE 0: CONFIGURACIÓN BASE (VIEW BINDING E INSETS)
        // =========================================================================
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // =========================================================================
        // BLOQUE 1: BARRA SUPERIOR (PASO 1 DEL MENÚ)
        // =========================================================================
        // SIEMPRE va después de inflar el 'binding'. Como los temas modernos usan
        // NoActionBar, registramos nuestra MaterialToolbar del XML como barra de la app.
        setSupportActionBar(binding.toolbar)

        // =========================================================================
        // BLOQUE 2: CUADRO DE DIÁLOGO (MaterialAlertDialogBuilder)
        // =========================================================================
        binding.btnCancelarPedido.setOnClickListener {
            MaterialAlertDialogBuilder(this) // Contexto de la Activity
                .setTitle("Cancelar Pedido") // Título superior
                .setMessage("¿Estás seguro de que quieres cancelar el pedido?") // Pregunta
                // Botón positivo (confirmar): '_' ignora los parámetros (dialog, which)
                .setPositiveButton("Sí, cancelar") { _, _ ->
                    binding.tvEstado.text = "Pedido cancelado"
                    binding.btnCancelarPedido.isEnabled = false
                    Toast.makeText(this, "Pedido anulado", Toast.LENGTH_SHORT).show()
                }
                // Botón negativo (rechazar): 'null' cierra el diálogo sin hacer nada
                .setNegativeButton("No", null)
                // OBLIGATORIO para que aparezca en pantalla
                .show()
        }
    }

    // =============================================================================
    // BLOQUE 3: MENÚ DE OPCIONES (PASOS 2 Y 3 DEL MENÚ - FUERA DE onCreate)
    // =============================================================================

    // PASO 2: Dibuja el menú en la barra inflando el archivo res/menu/menu_principal.xml
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true // 'true' indica a Android que debe mostrar el menú
    }

    // PASO 3: Detecta qué opción (<item>) ha pulsado el usuario mediante su ID
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reiniciar -> {
                binding.tvEstado.text = "Pedido pendiente"
                binding.btnCancelarPedido.isEnabled = true
                Toast.makeText(this, "Pedido reiniciado", Toast.LENGTH_SHORT).show()
                true // 'true' = clic gestionado por nosotros
            }
            R.id.action_acerca_de -> {
                // Reutilizamos un diálogo informativo (solo con botón positivo)
                MaterialAlertDialogBuilder(this)
                    .setTitle("Acerca de")
                    .setMessage("App de prácticas para el examen de PMDM")
                    .setPositiveButton("Aceptar", null)
                    .show()
                true
            }
            // Si no es ninguna de nuestras opciones (ej. flecha atrás de la barra),
            // dejamos que Android lo gestione por defecto
            else -> super.onOptionsItemSelected(item)
        }
    }
}