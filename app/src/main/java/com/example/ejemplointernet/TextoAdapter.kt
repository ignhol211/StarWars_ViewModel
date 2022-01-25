package com.example.ejemplointernet

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplointernet.databinding.ItemPlanetaBinding
import java.security.AccessController.getContext

class TextoAdapter(var listaPlanetas: List<Planeta>) : RecyclerView.Adapter <TextoAdapter.TextoViewHolder>() {

    class TextoViewHolder(var itemBinding : ItemPlanetaBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemPlanetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemBinding.tvNombrePlaneta.text = listaPlanetas[position].name
        holder.itemBinding.tvNombrePlaneta.setOnClickListener(){
            /*
            val intent = Intent (, TextoAdapter::class.java)
            intent.putExtra("url",listaPlanetas[position].url)
            startActivity(intent)
            todo
             */
        }
    }

    override fun getItemCount(): Int {
        return listaPlanetas.size
    }


}