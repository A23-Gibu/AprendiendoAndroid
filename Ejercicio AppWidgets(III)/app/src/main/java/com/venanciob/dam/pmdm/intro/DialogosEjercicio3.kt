package com.venanciob.dam.pmdm.intro

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venanciob.dam.pmdm.intro.databinding.DialogFechaBinding
import com.venanciob.dam.pmdm.intro.databinding.DialogHoraBinding
import com.venanciob.dam.pmdm.intro.databinding.DialogNombreBinding
import com.venanciob.dam.pmdm.intro.databinding.DialogPreguntaBinding
import com.venanciob.dam.pmdm.intro.databinding.DialogRecordatorioBinding

// 1. Recordatorio (tres botones)
class RecordatorioDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogRecordatorioBinding.inflate(layoutInflater)

        dialogBinding.btnMasTarde.setOnClickListener {
            enviarRespuesta(getString(R.string.mas_tarde))
            dismiss()
        }
        dialogBinding.btnNoGracias.setOnClickListener {
            enviarRespuesta(getString(R.string.no_gracias))
            dismiss()
        }
        dialogBinding.btnActualizar.setOnClickListener {
            enviarRespuesta(getString(R.string.actualizar))
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    private fun enviarRespuesta(opcion: String) {
        val msg = getString(R.string.resp_actualizacion, opcion)
        parentFragmentManager.setFragmentResult(
            PreguntaDialog.CLAVE_RESULTADO,
            bundleOf(PreguntaDialog.EXTRA_RESPUESTA to msg)
        )
    }

    companion object {
        const val TAG = "RecordatorioDialog"
    }
}

// 2. Tu nombre (layout propio)
class TuNombreDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogNombreBinding.inflate(layoutInflater)

        dialogBinding.btnCancelar.setOnClickListener { dismiss() }

        dialogBinding.btnAceptar.setOnClickListener {
            val nombre = dialogBinding.etNombreDialogo.text.toString().trim()
            if (nombre.isNotEmpty()) {
                val msg = getString(R.string.resp_nombre, nombre)
                parentFragmentManager.setFragmentResult(
                    PreguntaDialog.CLAVE_RESULTADO,
                    bundleOf(PreguntaDialog.EXTRA_RESPUESTA to msg)
                )
            }
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    companion object {
        const val TAG = "TuNombreDialog"
    }
}

// 3. Borrar respuesta (comunicación mediante INTERFAZ + onAttach)
class BorrarRespuestaDialog : DialogFragment() {

    interface BorrarRespuestaListener {
        fun onBorrarConfirmado()
    }

    private var listener: BorrarRespuestaListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as? BorrarRespuestaListener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Reutilizamos el XML dialog_pregunta cambiando los textos
        val dialogBinding = DialogPreguntaBinding.inflate(layoutInflater)
        dialogBinding.tvTituloDialogo.text = getString(R.string.titulo_borrar)
        dialogBinding.tvMensajeDialogo.text = getString(R.string.msg_borrar)
        dialogBinding.btnNo.text = getString(R.string.cancelar)
        dialogBinding.btnSi.text = getString(R.string.borrar)

        dialogBinding.btnNo.setOnClickListener { dismiss() }
        dialogBinding.btnSi.setOnClickListener {
            listener?.onBorrarConfirmado()
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    companion object {
        const val TAG = "BorrarRespuestaDialog"
    }
}

// 4. Elegir fecha (con layout propio DatePicker)
class ElegirFechaDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogFechaBinding.inflate(layoutInflater)

        dialogBinding.btnCancelar.setOnClickListener { dismiss() }

        dialogBinding.btnAceptar.setOnClickListener {
            val dia = dialogBinding.datePicker.dayOfMonth
            val mes = dialogBinding.datePicker.month + 1
            val anio = dialogBinding.datePicker.year
            val fecha = "%02d/%02d/%d".format(dia, mes, anio)
            val msg = getString(R.string.resp_fecha, fecha)
            parentFragmentManager.setFragmentResult(
                PreguntaDialog.CLAVE_RESULTADO,
                bundleOf(PreguntaDialog.EXTRA_RESPUESTA to msg)
            )
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    companion object {
        const val TAG = "ElegirFechaDialog"
    }
}

// 5. Elegir hora (con layout propio TimePicker)
class ElegirHoraDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogHoraBinding.inflate(layoutInflater)
        dialogBinding.timePicker.setIs24HourView(true)

        dialogBinding.btnCancelar.setOnClickListener { dismiss() }

        dialogBinding.btnAceptar.setOnClickListener {
            val hora = dialogBinding.timePicker.hour
            val minuto = dialogBinding.timePicker.minute
            val horaTexto = "%02d:%02d".format(hora, minuto)
            val msg = getString(R.string.resp_hora, horaTexto)
            parentFragmentManager.setFragmentResult(
                PreguntaDialog.CLAVE_RESULTADO,
                bundleOf(PreguntaDialog.EXTRA_RESPUESTA to msg)
            )
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    companion object {
        const val TAG = "ElegirHoraDialog"
    }
}