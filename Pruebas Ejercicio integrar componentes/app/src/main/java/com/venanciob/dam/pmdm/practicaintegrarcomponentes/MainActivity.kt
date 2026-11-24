package com.venanciob.dam.pmdm.practicaintegrarcomponentes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.venanciob.dam.pmdm.practicaintegrarcomponentes.databinding.ActivityMainBinding

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

        binding.btnCalcular.setOnClickListener {
            val textoImporte = binding.etImporte.text.toString()
            val importe = textoImporte.toDoubleOrNull()

            if (importe == null) {
                binding.etImporte.error = "Introduce un importe válido"
                return@setOnClickListener
            }

            val precio = if (binding.cbPropina.isChecked) importe * 1.10 else importe

            binding.tvResultado.text = getString(R.string.total_a_pagar, precio)
        }
    }
}