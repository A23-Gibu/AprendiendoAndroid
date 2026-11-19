package com.venanciob.dam.pmdm.intro

import android.app.Dialog
import android.os.Bundle
import android.widget.RadioButton
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venanciob.dam.pmdm.intro.databinding.DialogElegirUnaBinding

class ElegirUnaOpcionDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogElegirUnaBinding.inflate(layoutInflater)
        val plataformas = resources.getStringArray(R.array.plataformas)

        plataformas.forEachIndexed { indice, opcion ->
            val rb = RadioButton(requireContext()).apply {
                id = indice
                text = opcion
            }
            dialogBinding.rgPlataformas.addView(rb)
        }
        dialogBinding.rgPlataformas.check(0)

        dialogBinding.btnCancelar.setOnClickListener { dismiss() }

        dialogBinding.btnAceptar.setOnClickListener {
            val idMarcado = dialogBinding.rgPlataformas.checkedRadioButtonId
            val opcionElegida = plataformas[idMarcado]
            val respuesta = getString(R.string.resp_una_opcion, opcionElegida)
            parentFragmentManager.setFragmentResult(
                PreguntaDialog.CLAVE_RESULTADO,
                bundleOf(PreguntaDialog.EXTRA_RESPUESTA to respuesta)
            )
            dismiss()
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    companion object {
        const val TAG = "ElegirUnaOpcionDialog"
    }
}