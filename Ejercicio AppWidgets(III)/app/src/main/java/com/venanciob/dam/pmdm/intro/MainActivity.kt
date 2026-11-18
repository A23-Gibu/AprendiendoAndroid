package com.venanciob.dam.pmdm.intro

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.venanciob.dam.pmdm.intro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

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

        binding.btnHacerPregunta.setOnClickListener {
            PreguntaDialog().show(supportFragmentManager, PreguntaDialog.TAG)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PreguntaDialog.EXTRA_RESPUESTA, binding.tvRespuesta.text.toString())
    }
}