package com.example.taskbysmartobject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryAdapter(val list: List<String>, val onClick:(position:Int)->Unit): RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view= LayoutInflater.from(parent.context).inflate(R.layout.country_design,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position],onClick)
    }

    override fun getItemCount()= list.size
}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val text=itemView.findViewById<TextView>(R.id.textRecView)
    fun bind(country: String,onCLick: (position:Int) -> Unit){
        text.text=country
        itemView.setOnClickListener {
            onCLick(adapterPosition)
        }
    }
}
