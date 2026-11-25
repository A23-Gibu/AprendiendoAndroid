package com.example.pizzeriamenus

import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.dialog.MaterialAlertDialogBuilder

// 'open' permite que otras Activities hereden de esta clase para no repetir el código del menú
open class BaseActivity : AppCompatActivity() {

    // Cada pantalla hija sobrescribe esta propiedad para ocultar su propia opción en el menú
    protected open val opcionMenuActual: Int? = null
    protected open var ordenActualId: Int = R.id.orden_nombre

    // Configura la MaterialToolbar y decide si muestra la flecha 'Arriba' (<-)
    protected fun configurarToolbar(toolbar: MaterialToolbar, mostrarArriba: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(mostrarArriba)
    }

    // Infla el menú XML en la barra superior de todas las pantallas hijas
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    // Se ejecuta justo antes de mostrar el menú: permite ocultarlo o marcar opciones dinámicamente
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        // Si estamos en una pantalla concreta, ocultamos su enlace en el menú
        opcionMenuActual?.let { id -> menu.findItem(id)?.isVisible = false }
        // Marcamos el radio button seleccionado en el submenú de ordenación
        menu.findItem(ordenActualId)?.isChecked = true
        return super.onPrepareOptionsMenu(menu)
    }

    // Gestiona la navegación común al pulsar cualquier opción de la barra o los tres puntitos
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_inicio -> {
                startActivity(Intent(this, MainActivity::class.java))
                true
            }
            R.id.action_carta -> {
                startActivity(Intent(this, CartaActivity::class.java))
                true
            }
            R.id.action_carrito -> {
                startActivity(Intent(this, CarritoActivity::class.java))
                true
            }
            R.id.action_ajustes -> {
                startActivity(Intent(this, AjustesActivity::class.java))
                true
            }
            // Submenú con grupo de selección única (checkableBehavior="single")
            R.id.orden_nombre, R.id.orden_precio -> {
                item.isChecked = true
                ordenActualId = item.itemId
                Toast.makeText(this, "Ordenado ${item.title}", Toast.LENGTH_SHORT).show()
                true
            }
            // Diálogo informativo estándar de Material Design
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