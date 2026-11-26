package com.venanciob.dam.pmdm.widgets_iv

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.venanciob.dam.pmdm.widgets_iv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Barra de título: el icono se pone en el XML con app:logo
        setSupportActionBar(binding.toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val barras = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(barras.left, 0, barras.right, barras.bottom)
            binding.toolbar.setPadding(0, barras.top, 0, 0)
            insets
        }

        configurarLista()
    }

    private fun configurarLista() {
        // 1. Datos: se leen del fichero res/values/provincias.xml
        //    (para ver solo las de Castilla y León: R.array.provincias_cyl)
        val provincias = resources.getStringArray(R.array.provincias_espana)

        // 2. Adaptador: une los datos con el layout de cada fila
        val adaptador = ArrayAdapter(this, R.layout.fila_provincia, provincias)
        binding.lvProvincias.adapter = adaptador

        // 3. Escuchador: se ejecuta al pulsar una fila.
        //    parent = el ListView, view = la fila, position = índice, id = id de la fila
        binding.lvProvincias.setOnItemClickListener { _, _, position, _ ->
            binding.tvResultado.text = getString(R.string.has_elegido, provincias[position])
        }
    }
}
