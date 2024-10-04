package com.rohit.feildseye.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rohit.feildseye.R

class EcommerceAdapter(private val ItemsList: List<String>) :
    RecyclerView.Adapter<EcommerceAdapter.LoanViewHolder>() {
    private lateinit var mlistener : onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }
    fun setOnItemClickListener(listener: Any){
        mlistener = listener as onItemClickListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_ecomm_item, parent, false)
        return LoanViewHolder(view, mlistener)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(ItemsList[position])
        holder.itemView.setOnClickListener {
            if(position == 0){
                mlistener.onItemClick(position)
            }else if (position == 1){
                mlistener.onItemClick(position)
            }else if (position == 2){
                mlistener.onItemClick(position)
            }else if (position == 3){
                mlistener.onItemClick(position)
            }else if (position == 4){
                mlistener.onItemClick(position)
            }
        }
    }

    override fun getItemCount(): Int = ItemsList.size

    inner class LoanViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val EcomImageView : ImageView = itemView.findViewById(R.id.ecommImage)
        val EcomTitleTextView: TextView = itemView.findViewById(R.id.ecommtitle)
        val EcomRating: RatingBar = itemView.findViewById(R.id.ecommRating)
        val EcomPriceTextView: TextView = itemView.findViewById(R.id.ecommPrice)

        init {
            itemView.setOnClickListener{ v: View ->
                val position: Int= adapterPosition
                Toast.makeText(itemView.context, "You clicked on item ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(loan: String) {
            if(position == 0) {
                EcomTitleTextView.text = "Agriculture Tractor"
                EcomPriceTextView.text = "2.4lakh~33.9lakh"
                EcomRating.rating = 4.5f
                EcomImageView.setImageResource(R.drawable.tractor)
            }else if (position == 1){
                EcomTitleTextView.text = "Agriculture Fertilizer"
                EcomPriceTextView.text = "Product basis"
                EcomRating.rating = 2f
                EcomImageView.setImageResource(R.drawable.soilfertilizer)
            }else if (position == 2){
                EcomTitleTextView.text = "Mild Steel Tractor Cultivator"
                EcomPriceTextView.text = "38,500 INR"
                EcomRating.rating = 3f
                EcomImageView.setImageResource(R.drawable.propelowing)
            }else if (position == 3){
                EcomTitleTextView.text = "Agriculture Drone"
                EcomPriceTextView.text = "39,001.00 ~ 7.5lakh"
                EcomRating.rating = 5f
                EcomImageView.setImageResource(R.drawable.drone)
            }else if (position == 4){
                EcomTitleTextView.text = "Agriculture Seeder"
                EcomPriceTextView.text = "75000.00 INR"
                EcomRating.rating = 5f
                EcomImageView.setImageResource(R.drawable.seeding)
            }

        }
    }
}
