package com.example.blisstest.list.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blisstest.R
import com.squareup.picasso.Picasso

class ListItemHolder (itemView: View, private val removeFunction: (position: Int) -> Unit): RecyclerView.ViewHolder(itemView) {

    val tvDescription = itemView.findViewById<TextView>(R.id.row_emoji_tv)
    val ivIcon = itemView.findViewById<ImageView>(R.id.row_emoji_iv)

    init {
        itemView.setOnClickListener { removeFunction.invoke(adapterPosition) }
    }

    fun bind(item: ListItem) {
        tvDescription.text = item.getDescriptionText()
        Picasso
            .get()
            .load(item.getIconUrl())
            .into(ivIcon)
    }
}