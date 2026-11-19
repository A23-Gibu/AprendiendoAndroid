package com.venanciob.dam.pmdm.intro

import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.venanciob.dam.pmdm.intro.databinding.DialogListaElementosBinding

class ListaElementosDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = DialogListaElementosBinding.inflate(layoutInflater)
        val plataformas = resources.getStringArray(R.array.plataformas)

        plataformas.forEach { opcion ->
            val tvItem = TextView(requireContext()).apply {
                text = opcion
                textSize = 16f
                setPadding(16, 28, 16, 28)
                setOnClickListener {
                    val respuesta = getString(R.string.resp_una_opcion, opcion)
                    parentFragmentManager.setFragmentResult(
                        PreguntaDialog.CLAVE_RESULTADO,
                        bundleOf(PreguntaDialog.EXTRA_RESPUESTA to respuesta)
                    )
                    dismiss()
                }
            }
            dialogBinding.contenedorLista.addView(tvItem)
        }

        return MaterialAlertDialogBuilder(requireContext())
            .setView(dialogBinding.root)
            .create()
    }

    companion object {
        const val TAG = "ListaElementosDialog"
    }
}