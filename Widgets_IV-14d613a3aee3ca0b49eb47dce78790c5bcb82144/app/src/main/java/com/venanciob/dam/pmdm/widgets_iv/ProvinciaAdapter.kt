package com.venanciob.dam.pmdm.widgets_iv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.venanciob.dam.pmdm.widgets_iv.databinding.FilaProvinciaBinding

class ProvinciaAdapter(
    private val provincias: List<String>,
    private val alPulsar: (String) -> Unit
) : RecyclerView.Adapter<ProvinciaAdapter.ProvinciaViewHolder>() {

    // 1. EL MOLDE (ViewHolder): Guarda la referencia a las vistas de UNA fila para no buscarlas todo el rato
    class ProvinciaViewHolder(val binding: FilaProvinciaBinding) :
        RecyclerView.ViewHolder(binding.root)

    // 2. CREAR CAJA NUEVA: Solo se llama unas pocas veces al principio (para las filas que caben en pantalla)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProvinciaViewHolder {
        val binding = FilaProvinciaBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ProvinciaViewHolder(binding)
    }

    // 3. RELLENAR / RECICLAR: Se llama cada vez que una fila entra en pantalla al hacer scroll
    override fun onBindViewHolder(holder: ProvinciaViewHolder, position: Int) {
        val provincia = provincias[position]
        holder.binding.root.text = provincia
        holder.binding.root.setOnClickListener { alPulsar(provincia) }
    }

    // 4. CONTAR: Indica al RecyclerView cuántas filas hay en total
    override fun getItemCount(): Int = provincias.size
}