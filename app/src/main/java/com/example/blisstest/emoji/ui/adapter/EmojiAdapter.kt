package com.example.blisstest.emoji.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blisstest.R
import com.example.blisstest.util.data.model.Emoji

class EmojiAdapter: RecyclerView.Adapter<EmojiHolder>() {

    private val items = mutableListOf<Emoji>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmojiHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_emoji, parent, false)
        return EmojiHolder(view) { removeItem(it) }
    }

    override fun onBindViewHolder(holder: EmojiHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    private fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun refreshData(emojis: List<Emoji>) {
        this.items.clear()
        this.items.addAll(emojis)

        notifyDataSetChanged()
    }
}
