package com.example.ejerciciocontextuales

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu // ⚠️ IMPORTANTE: usar el de androidx.appcompat
import com.example.ejerciciocontextuales.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // A. MENÚ CONTEXTUAL (PULSACIÓN LARGA SOBRE UNA VISTA)

        // PASO 1: Registrar la vista que responderá a la pulsación larga
        registerForContextMenu(binding.tvElemento)


        // B. POPUP MENU (CLIC NORMAL SOBRE UN BOTÓN - TODO VA AQUÍ DENTRO)

        binding.btnOpciones.setOnClickListener { botonPulsado ->
            // Creamos el menú anclado al botón que se acaba de pulsar
            val popup = PopupMenu(this, botonPulsado)
            popup.menuInflater.inflate(R.menu.menu_contextual, popup.menu)

            // Escuchamos qué opción se elige
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.ctx_editar -> {
                        Toast.makeText(this, "Editando desde Popup", Toast.LENGTH_SHORT).show()
                        true
                    }
                    R.id.ctx_borrar -> {
                        Toast.makeText(this, "Borrando desde Popup", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
            popup.show() // mostrarlo
        }
    }

    // PASO 2 DEL MENÚ CONTEXTUAL: Dibuja el menú al mantener pulsada la vista registrada
    override fun onCreateContextMenu(
        menu: ContextMenu,
        v: View,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_contextual, menu)
        menu.setHeaderTitle("Opciones del elemento") // Título opcional arriba del menú
    }

    // PASO 3 DEL MENÚ CONTEXTUAL: Responde al clic en una opción del menú flotante
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.ctx_editar -> {
                Toast.makeText(this, "Editando elemento", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.ctx_borrar -> {
                binding.tvElemento.text = "Elemento borrado"
                true
            }
            R.id.ctx_compartir -> {
                // BONUS: Intent implícito para compartir texto con selector de apps
                val sendIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_TEXT, binding.tvElemento.text.toString())
                }
                startActivity(Intent.createChooser(sendIntent, "Compartir con..."))
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
}