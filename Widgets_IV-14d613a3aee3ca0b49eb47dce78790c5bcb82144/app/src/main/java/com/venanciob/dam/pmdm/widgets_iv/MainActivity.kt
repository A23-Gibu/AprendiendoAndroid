package com.venanciob.dam.pmdm.widgets_iv

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.venanciob.dam.pmdm.widgets_iv.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
        // 1. Datos convertidos a List<String>
        val provincias = resources.getStringArray(R.array.provincias_espana).toList()

        // 2. LayoutManager: obligatorio para decirle que coloque las filas en vertical
        binding.rvProvincias.layoutManager = LinearLayoutManager(this)

        // 3. ItemDecoration: añade la línea separadora entre filas (antes lo hacía android:divider)
        binding.rvProvincias.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        // 4. Adaptador: le pasamos la lista y la función lambda que se ejecutará al pulsar una fila
        binding.rvProvincias.adapter = ProvinciaAdapter(provincias) { provincia ->
            binding.tvResultado.text = getString(R.string.has_elegido, provincia)
        }
    }
}