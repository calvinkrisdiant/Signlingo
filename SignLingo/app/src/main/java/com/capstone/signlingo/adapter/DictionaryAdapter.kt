package com.capstone.signlingo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.signlingo.R
import com.capstone.signlingo.data.Letter

class DictionaryAdapter(
    private val letters: List<Letter>,
    private val clickListener: (Letter) -> Unit
) : RecyclerView.Adapter<DictionaryAdapter.LetterViewHolder>() {

    inner class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val letterImage: ImageView = itemView.findViewById(R.id.letterImage)
        val letterTitle: TextView = itemView.findViewById(R.id.letterTitle)

        fun bind(letter: Letter, clickListener: (Letter) -> Unit) {
            letterImage.setImageResource(letter.imageResId)
            letterTitle.text = letter.title
            itemView.setOnClickListener { clickListener(letter) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_letter, parent, false)
        return LetterViewHolder(view)
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        holder.bind(letters[position], clickListener)
    }

    override fun getItemCount(): Int {
        return letters.size
    }
}
