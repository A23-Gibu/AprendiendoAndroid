package com.venanciob.dam.pmdm.intro

import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venanciob.dam.pmdm.intro.databinding.DialogPreguntaBinding

class PreguntaDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogPreguntaBinding.inflate(layoutInflater)

        dialogBinding.btnSi.setOnClickListener {
            enviarRespuesta(getString(R.string.resp_si))
            dismiss()
        }

        dialogBinding.btnNo.setOnClickListener {
            enviarRespuesta(getString(R.string.resp_no))
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    private fun enviarRespuesta(texto: String) {
        parentFragmentManager.setFragmentResult(CLAVE_RESULTADO, bundleOf(EXTRA_RESPUESTA to texto))
    }

    companion object {
        const val TAG = "PreguntaDialog"
        const val CLAVE_RESULTADO = "resultado_pregunta"
        const val EXTRA_RESPUESTA = "respuesta"
    }
}