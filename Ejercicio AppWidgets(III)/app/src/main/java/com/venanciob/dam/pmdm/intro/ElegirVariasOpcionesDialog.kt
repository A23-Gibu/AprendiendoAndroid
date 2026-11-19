package com.venanciob.dam.pmdm.intro

import android.app.Dialog
import android.os.Bundle
import android.widget.CheckBox
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venanciob.dam.pmdm.intro.databinding.DialogElegirVariasBinding

class ElegirVariasOpcionesDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogElegirVariasBinding.inflate(layoutInflater)
        val plataformas = resources.getStringArray(R.array.plataformas)

        val listaCheckBoxes = plataformas.map { opcion ->
            CheckBox(requireContext()).apply {
                text = opcion
                dialogBinding.contenedorCheckboxes.addView(this)
            }
        }

        dialogBinding.btnCancelar.setOnClickListener { dismiss() }

        dialogBinding.btnAceptar.setOnClickListener {
            val seleccionadas = listaCheckBoxes
                .filter { it.isChecked }
                .map { it.text.toString() }

            val textoOpciones = if (seleccionadas.isEmpty()) getString(R.string.ninguna)
            else seleccionadas.joinToString(", ")

            val respuesta = getString(R.string.resp_varias_opciones, textoOpciones)
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
        const val TAG = "ElegirVariasOpcionesDialog"
    }
}