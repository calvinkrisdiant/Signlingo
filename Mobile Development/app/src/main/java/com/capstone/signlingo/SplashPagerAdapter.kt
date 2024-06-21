//package com.capstone.signlingo
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.splash_screen_item.view.*
//
//class SplashPagerAdapter(private val items: List<String>) : RecyclerView.Adapter<SplashPagerAdapter.ViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.splash_screen_item, parent, false)
//        return ViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//
//    override fun getItemCount(): Int = items.size
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        fun bind(text: String) {
//            itemView.splashText.text = text
//        }
//    }
//}
