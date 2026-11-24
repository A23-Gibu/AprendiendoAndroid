package com.example.ejercicionotificaciones

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicionotificaciones.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


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

            Snackbar.make(binding.root, "Cálculo realizado", Snackbar.LENGTH_LONG)
                .setAction("Reiniciar") {
                    // Código que se ejecuta si el usuario pulsa "Limpiar"
                    binding.cbPropina.isChecked = false
                    binding.tvResultado.text = ""
                    binding.etImporte.text.clear()

                    Toast.makeText(this, "Formulario reiniciado", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}