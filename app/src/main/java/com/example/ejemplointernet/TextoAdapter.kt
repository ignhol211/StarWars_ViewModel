package com.example.ejemplointernet

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ejemplointernet.databinding.ItemPlanetaBinding

class TextoAdapter(var listaPlanetas: List<Planeta>) : RecyclerView.Adapter <TextoAdapter.TextoViewHolder>() {

    class TextoViewHolder(var itemBinding : ItemPlanetaBinding) : RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextoViewHolder {
        val binding = ItemPlanetaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TextoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TextoViewHolder, position: Int) {
        holder.itemBinding.tvNombrePlaneta.text = listaPlanetas[position].name

        holder.itemBinding.tvNombrePlaneta.setOnClickListener(){

            val intent = Intent (holder.itemBinding.root.context, SecondActivity::class.java)
            intent.putExtra("url",listaPlanetas[position].url)
            holder.itemBinding.root.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listaPlanetas.size
    }
}