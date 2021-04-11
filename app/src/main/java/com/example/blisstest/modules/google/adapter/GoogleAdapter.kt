package com.example.blisstest.modules.google.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.blisstest.R
import com.example.blisstest.util.data.model.GoogleRepos

private const val VIEW_TYPE_LOADING = 0
private const val VIEW_TYPE_NORMAL = 1
class GoogleAdapter2: PagedListAdapter<GoogleRepos, GoogleHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoogleHolder {
        return GoogleHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.row_google_repos,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: GoogleHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GoogleRepos>() {
            override fun areItemsTheSame(oldItem: GoogleRepos, newItem: GoogleRepos): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GoogleRepos, newItem: GoogleRepos): Boolean {
                return oldItem == newItem
            }
        }
    }

}