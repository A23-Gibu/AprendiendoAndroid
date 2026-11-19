package com.venanciob.dam.pmdm.intro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.venanciob.dam.pmdm.intro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BorrarRespuestaDialog.BorrarRespuestaListener {

    private lateinit var binding: ActivityMainBinding

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

        savedInstanceState?.getString(PreguntaDialog.EXTRA_RESPUESTA)?.let {
            binding.tvRespuesta.text = it
        }

        supportFragmentManager.setFragmentResultListener(
            PreguntaDialog.CLAVE_RESULTADO, this
        ) { _, resultado ->
            binding.tvRespuesta.text = resultado.getString(PreguntaDialog.EXTRA_RESPUESTA)
        }

        // Botones Ejercicio 2
        binding.btnPreguntaSiNo.setOnClickListener {
            PreguntaDialog().show(supportFragmentManager, PreguntaDialog.TAG)
        }
        binding.btnListaElementos.setOnClickListener {
            ListaElementosDialog().show(supportFragmentManager, ListaElementosDialog.TAG)
        }
        binding.btnElegirUna.setOnClickListener {
            ElegirUnaOpcionDialog().show(supportFragmentManager, ElegirUnaOpcionDialog.TAG)
        }
        binding.btnElegirVarias.setOnClickListener {
            ElegirVariasOpcionesDialog().show(supportFragmentManager, ElegirVariasOpcionesDialog.TAG)
        }

        // Botones Ejercicio 3
        binding.btnRecordatorio.setOnClickListener {
            RecordatorioDialog().show(supportFragmentManager, RecordatorioDialog.TAG)
        }
        binding.btnTuNombre.setOnClickListener {
            TuNombreDialog().show(supportFragmentManager, TuNombreDialog.TAG)
        }
        binding.btnBorrarRespuesta.setOnClickListener {
            BorrarRespuestaDialog().show(supportFragmentManager, BorrarRespuestaDialog.TAG)
        }
        binding.btnElegirFecha.setOnClickListener {
            ElegirFechaDialog().show(supportFragmentManager, ElegirFechaDialog.TAG)
        }
        binding.btnElegirHora.setOnClickListener {
            ElegirHoraDialog().show(supportFragmentManager, ElegirHoraDialog.TAG)
        }
    }

    // Callback de la interfaz BorrarRespuestaListener
    override fun onBorrarConfirmado() {
        binding.tvRespuesta.text = ""
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PreguntaDialog.EXTRA_RESPUESTA, binding.tvRespuesta.text.toString())
    }
}