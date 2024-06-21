package com.capstone.signlingo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.signlingo.R
import com.capstone.signlingo.data.Tips

class TipsAdapter(private val tipsList: List<Tips>) : RecyclerView.Adapter<TipsAdapter.TipsViewHolder>() {

    inner class TipsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tipsTitle)
        val description: TextView = itemView.findViewById(R.id.tipsDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TipsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tips, parent, false)
        return TipsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TipsViewHolder, position: Int) {
        val tips = tipsList[position]
        holder.title.text = tips.title
        holder.description.text = tips.description
    }

    override fun getItemCount(): Int {
        return tipsList.size
    }
}
