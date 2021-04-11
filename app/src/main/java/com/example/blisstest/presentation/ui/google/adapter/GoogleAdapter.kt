package com.example.blisstest.presentation.ui.google.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.blisstest.R
import com.example.blisstest.util.model.GoogleRepos

class GoogleAdapter: PagedListAdapter<GoogleRepos, GoogleHolder>(DIFF_CALLBACK) {

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