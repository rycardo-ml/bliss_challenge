package com.example.blisstest.util.ui.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.blisstest.R
import com.example.blisstest.util.ChangedItem
import com.example.blisstest.util.ChangedItemType

class ListItemAdapter<T: ListItem>(private val removeFunction: (position: Int) -> Unit): RecyclerView.Adapter<ListItemHolder>() {

    private val items = mutableListOf<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_emoji, parent, false)
        return ListItemHolder(view, removeFunction)
    }

    override fun onBindViewHolder(holder: ListItemHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun refreshData(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)

        notifyDataSetChanged()
    }

    fun updateChangedItems(items: List<ChangedItem>) {

        items.forEach {
            when (it.type) {
                ChangedItemType.REMOVE -> {
                    removeItem(it.index)
                    return@forEach
                }

                ChangedItemType.INSERT -> {
                    //TODO implements
                    return@forEach
                }

                ChangedItemType.UPDATE -> {
                    // TODO implement
                    return@forEach
                }
            }
        }
    }
}
