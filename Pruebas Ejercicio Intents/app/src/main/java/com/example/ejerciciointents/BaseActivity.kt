package com.example.ejerciciointents

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

// 'open' es OBLIGATORIO en Kotlin para que otras actividades puedan heredar de esta clase
open class BaseActivity : AppCompatActivity() {

    // Propiedad que cada hija puede sobrescribir para ocultar su propia opción en el menú
    protected open val opcionMenuActual: Int? = null

    // Método auxiliar para configurar la barra (y mostrar o no la flecha 'Arriba' <-)
    protected fun configurarToolbar(toolbar: MaterialToolbar, mostrarArriba: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(mostrarArriba)
    }

    // 1. Infla el menú común en todas las pantallas hijas
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    // 2. Oculta la opción de la pantalla en la que ya estamos (para no abrirla dos veces)
    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        opcionMenuActual?.let { id -> menu.findItem(id)?.isVisible = false }
        return super.onPrepareOptionsMenu(menu)
    }

    // 3. Gestiona los clics del menú común
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_inicio -> {
                // INTENT EXPLÍCITO CON FLAGS:
                // CLEAR_TOP + SINGLE_TOP cierra lo que haya encima y vuelve a la MainActivity
                // existente sin crear una copia duplicada en la pila.
                val intent = Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                }
                startActivity(intent)
                true
            }
            R.id.action_web -> {
                // INTENT IMPLÍCITO: pide al sistema que abra una URL (no indicamos clase)
                val intentWeb = Intent(Intent.ACTION_VIEW, Uri.parse("https://developer.android.com"))
                try {
                    startActivity(intentWeb)
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(this, "No hay navegador instalado", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item) // Necesario para que funcione la flecha 'Arriba'
        }
    }
}