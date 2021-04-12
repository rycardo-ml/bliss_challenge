package com.example.blisstest.presentation.ui.google.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.blisstest.R
import com.example.blisstest.util.model.GoogleRepos

class GoogleHolder(item: View): RecyclerView.ViewHolder(item) {

    private val tvDescription = itemView.findViewById<TextView>(R.id.row_googlerepos_tv)

    fun bind(item: GoogleRepos) {
        tvDescription.text = item.name
    }
}