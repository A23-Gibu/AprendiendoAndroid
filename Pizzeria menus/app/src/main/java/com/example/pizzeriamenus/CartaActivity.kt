package com.example.pizzeriamenus

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pizzeriamenus.databinding.ActivityCartaBinding

class CartaActivity : BaseActivity() {

    private lateinit var binding: ActivityCartaBinding
    override val opcionMenuActual = R.id.action_carta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityCartaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Activa la flecha de retorno en la Toolbar
        configurarToolbar(binding.toolbar, mostrarArriba = true)
    }
}