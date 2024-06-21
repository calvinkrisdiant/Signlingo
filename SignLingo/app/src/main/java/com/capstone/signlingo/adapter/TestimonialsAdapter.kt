package com.capstone.signlingo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.capstone.signlingo.R
import com.capstone.signlingo.data.Testimonial

class TestimonialsAdapter(private val testimonialsList: List<Testimonial>) : RecyclerView.Adapter<TestimonialsAdapter.TestimonialViewHolder>() {

    inner class TestimonialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.testimonialName)
        val feedback: TextView = itemView.findViewById(R.id.testimonialFeedback)
        val rating: RatingBar = itemView.findViewById(R.id.testimonialRating)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestimonialViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_testimonial, parent, false)
        return TestimonialViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestimonialViewHolder, position: Int) {
        val testimonial = testimonialsList[position]
        holder.name.text = testimonial.name
        holder.feedback.text = testimonial.feedback
        holder.rating.rating = testimonial.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return testimonialsList.size
    }
}
