package com.example.pizzeriamenus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pizzeriamenus.databinding.ActivityCarritoBinding

class CarritoActivity : BaseActivity() {

    private lateinit var binding: ActivityCarritoBinding
    override val opcionMenuActual = R.id.action_carrito

    // Constantes para las claves de los extras (evita errores de escritura entre Activities)
    companion object {
        const val EXTRA_NUM_PIZZAS = "extra_num_pizzas"
        const val EXTRA_RESUMEN = "extra_resumen"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCarritoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Mostramos la flecha 'Arriba' (<-) que vuelve a MainActivity según el AndroidManifest.xml
        configurarToolbar(binding.toolbar, mostrarArriba = true)

        // Recuperamos los datos enviados desde MainActivity mediante la propiedad 'intent'
        val num = intent.getIntExtra(EXTRA_NUM_PIZZAS, 0)
        val resumen = intent.getStringExtra(EXTRA_RESUMEN) ?: "Sin datos"
        binding.tvDetalleCarrito.text = "Recibido: $resumen (Total: $num)"
    }
}