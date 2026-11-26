package com.example.ejercicionotificaciones

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejercicionotificaciones.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // 3. Preparamos el "lanzador" que abrirá la ventana del sistema pidiendo permiso al usuario
    //    y recogerá su respuesta (true si aceptó, false si lo rechazó)
    private val lanzadorPermiso = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        if (concedido) {
            Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sin permiso no verás notificaciones", Toast.LENGTH_SHORT).show()
        }
    }

    // 4. Creamos la función que registra el Canal de Notificaciones (obligatorio desde Android 8 / API 26)
    private fun crearCanalNotificaciones() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                "canal_cuentas", // ID interno del canal (debe coincidir luego en el Builder)
                "Cálculo de cuentas", // Nombre visible para el usuario en los Ajustes del móvil
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(canal)
        }
    }

    // 5. Creamos la función que comprueba el permiso, construye la notificación y la dispara
    private fun lanzarNotificacion(total: Double) {
        // 5.1. Si el móvil es Android 13 (TIRAMISU) o superior, comprobamos si ya tenemos permiso
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                // Si NO tenemos permiso, lanzamos la ventana del paso 3 y salimos sin notificar aún
                lanzadorPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                return
            }
        }

        // 5.2. Construimos la notificación asociándola al canal "canal_cuentas" del paso 4
        val notificacion = NotificationCompat.Builder(this, "canal_cuentas")
            .setSmallIcon(R.drawable.ic_notification) // El icono XML del paso 2
            .setContentTitle("Cuenta calculada")
            .setContentText("El total a pagar es de %.2f €".format(total))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Hace que la notificación desaparezca al tocarla
            .build()

        // 5.3. Enviamos la notificación al sistema (el '1' es su ID por si luego queremos actualizarla o borrarla)
        NotificationManagerCompat.from(this).notify(1, notificacion)
    }

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

        // 6. Llamamos a crearCanalNotificaciones() nada más arrancar la pantalla
        crearCanalNotificaciones()

        binding.btnCalcular.setOnClickListener {
            val textoImporte = binding.etImporte.text.toString()
            val importe = textoImporte.toDoubleOrNull()

            if (importe == null) {
                binding.etImporte.error = "Introduce un importe válido"
                return@setOnClickListener
            }

            val precio = if (binding.cbPropina.isChecked) importe * 1.10 else importe

            binding.tvResultado.text = getString(R.string.total_a_pagar, precio)

            // 7. Disparamos la notificación del sistema con el resultado calculado
            lanzarNotificacion(precio)

            // Snackbar (barra que sale desde abajo con un aviso y te permite realizar una acción)
            Snackbar.make(binding.root, "Cálculo realizado", Snackbar.LENGTH_LONG)
                .setAction("Reiniciar") {
                    binding.cbPropina.isChecked = false
                    binding.tvResultado.text = ""
                    binding.etImporte.text.clear()
                    // Toast (aviso pequeño centrado abajo que informa brevemente al usuario)
                    Toast.makeText(this, "Formulario reiniciado", Toast.LENGTH_SHORT).show()
                }
                .show()
        }
    }
}