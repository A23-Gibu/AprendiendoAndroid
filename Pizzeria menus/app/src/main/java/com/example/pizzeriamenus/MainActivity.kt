package com.example.pizzeriamenus

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pizzeriamenus.databinding.ActivityMainBinding

// Hereda de BaseActivity para tener automáticamente la Toolbar y el menú
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private var numPizzas = 0

    // Oculta la opción "Inicio" del menú porque ya estamos en la pantalla principal
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

        // En la pantalla raíz NO mostramos la flecha 'Arriba' (mostrarArriba = false)
        configurarToolbar(binding.toolbar, mostrarArriba = false)

        binding.btnAnadirPizza.setOnClickListener {
            numPizzas++
            actualizarUI()
        }

        binding.btnVaciarCarrito.setOnClickListener {
            numPizzas = 0
            actualizarUI()
        }
    }

    private fun actualizarUI() {
        binding.tvResumenPedido.text = if (numPizzas == 0) {
            "Carrito vacío (0 pizzas)"
        } else {
            "Pedido actual: $numPizzas pizza(s)"
        }
        // Fuerza a Android a volver a ejecutar onPrepareOptionsMenu() para actualizar iconos
        invalidateOptionsMenu()
    }

    // Sobrescribimos onPrepareOptionsMenu para ocultar el carrito cuando está vacío (0 pizzas)
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menu.findItem(R.id.action_carrito)?.isVisible = (numPizzas > 0)
        return super.onPrepareOptionsMenu(menu)
    }

    // Sobrescribimos solo el clic del carrito para enviar los datos con putExtra; el resto lo hace BaseActivity
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_carrito) {
            val intent = Intent(this, CarritoActivity::class.java).apply {
                putExtra(CarritoActivity.EXTRA_NUM_PIZZAS, numPizzas)
                putExtra(CarritoActivity.EXTRA_RESUMEN, binding.tvResumenPedido.text.toString())
            }
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}