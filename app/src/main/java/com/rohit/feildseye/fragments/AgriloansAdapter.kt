package com.rohit.feildseye.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.rohit.feildseye.R

class AgriloansAdapter(private val loansList: List<String>) :
    RecyclerView.Adapter<AgriloansAdapter.LoanViewHolder>() {
        private lateinit var mlistener : onItemClickListener
        interface onItemClickListener{
            fun onItemClick(position: Int)
        }
    fun setOnItemClickListener(listener: Any){
        mlistener = listener as onItemClickListener

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.single_yojna_list, parent, false)
        return LoanViewHolder(view, mlistener)
    }

    override fun onBindViewHolder(holder: LoanViewHolder, position: Int) {
        holder.bind(loansList[position])
        holder.itemView.setOnClickListener {
           if(position == 0){
               mlistener.onItemClick(position)
           }else if (position == 1){
               mlistener.onItemClick(position)
           }else if (position == 2){
               mlistener.onItemClick(position)
           }else if (position == 3){
               mlistener.onItemClick(position)
           }
        }
    }

    override fun getItemCount(): Int = loansList.size

    inner class LoanViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
         val loanTitleTextView: TextView = itemView.findViewById(R.id.loanstitle)
         val loanDateTextView: TextView = itemView.findViewById(R.id.loansDate)
         val loanImageView: ImageView = itemView.findViewById(R.id.loansImg)

        init {
            itemView.setOnClickListener{ v: View ->
                val position: Int= adapterPosition
                Toast.makeText(itemView.context, "You clicked on item ${position + 1}", Toast.LENGTH_SHORT).show()
            }
        }
        fun bind(loan: String) {
            if(position == 0) {
                loanTitleTextView.text = loan
                loanDateTextView.text = "21 january 2019"
                loanImageView.setImageResource(R.drawable.loans)
            }else if (position == 1){
                loanTitleTextView.text = "Pradhan Mantri Fasal Bima Yojana"
                loanDateTextView.text = "22 August 2021"
                loanImageView.setImageResource(R.drawable.loans)
            }else if (position == 2){
                loanTitleTextView.text = "Soil Health Management Scheme"
                loanDateTextView.text = "3 December 2021"
                loanImageView.setImageResource(R.drawable.loans)
            }else if (position == 3){
                loanTitleTextView.text = "Paramparagat Krishi Vikas Yojana"
                loanDateTextView.text = "15 june 2020"
                loanImageView.setImageResource(R.drawable.loans)
            }

        }
    }
}
