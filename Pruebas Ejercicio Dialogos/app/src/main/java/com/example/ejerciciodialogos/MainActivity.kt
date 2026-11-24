package com.example.ejerciciodialogos

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejerciciodialogos.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    // 1. Declaramos la variable de View Binding (se inicializa en onCreate)
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 2. Inflamos el layout XML a través del binding y lo fijamos como vista raíz
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Ajustamos los márgenes para que la barra de estado y navegación no tapen la UI
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. Listener del botón para lanzar el cuadro de diálogo
        binding.btnCancelarPedido.setOnClickListener {

            // 5. Construimos el diálogo de Material 3 pasando 'this' (el contexto de la Activity)
            MaterialAlertDialogBuilder(this)
                .setTitle("Cancelar Pedido") // Título en negrita arriba
                .setMessage("¿Estás seguro de que quieres cancelar el pedido?") // Texto explicativo

                // 6. Botón de confirmación (Positivo): recibe 2 parámetros (dialog, which) que ignoramos con '_'
                .setPositiveButton("Sí, cancelar") { _, _ ->
                    binding.tvEstado.text = "Pedido cancelado"       // Actualizamos el texto
                    binding.btnCancelarPedido.isEnabled = false      // Desactivamos el botón para no repetir
                    Toast.makeText(this, "Pedido anulado", Toast.LENGTH_SHORT).show() // Aviso breve abajo
                }

                // 7. Botón de cancelación (Negativo): al pasar 'null', simplemente cierra el diálogo sin hacer nada
                .setNegativeButton("No", null)

                // 8. OBLIGATORIO: si no se llama a .show(), el diálogo no aparece en pantalla
                .show()
        }
    }
}