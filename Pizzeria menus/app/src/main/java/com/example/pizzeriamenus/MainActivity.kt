package com.example.pizzeriamenus

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pizzeriamenus.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var numPizzas = 0
    private var ordenActualId = R.id.orden_nombre

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

        // 1. Registramos la MaterialToolbar como barra de acción de la Activity
        setSupportActionBar(binding.toolbar)

        // Simulación de añadir/vaciar pizzas para probar el carrito dinámico
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
        // Obliga a Android a volver a ejecutar onPrepareOptionsMenu() para ocultar/mostrar el carrito
        invalidateOptionsMenu()
    }

    // 2. Inflamos el menú en la barra superior
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    // 3. Modificamos el menú dinámicamente justo antes de mostrarse
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // El icono del carrito solo aparece si hay al menos 1 pizza
        menu.findItem(R.id.action_carrito)?.isVisible = (numPizzas > 0)
        // Marcamos el radio button que corresponda en el submenú de ordenación
        menu.findItem(ordenActualId)?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }

    // 4. Gestionamos los clics en cada opción del menú
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_carrito -> {
                Toast.makeText(this, "Abriendo carrito ($numPizzas pizzas)...", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_carta -> {
                Toast.makeText(this, "Abriendo carta...", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.orden_nombre, R.id.orden_precio -> {
                item.isChecked = true
                ordenActualId = item.itemId
                Toast.makeText(this, "Ordenado ${item.title}", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_ajustes -> {
                Toast.makeText(this, "Abriendo ajustes...", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_acerca_de -> {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Pizzería DAM")
                    .setMessage("Versión 1.0 - Práctica de Menús y Navegación")
                    .setPositiveButton("Aceptar", null)
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}