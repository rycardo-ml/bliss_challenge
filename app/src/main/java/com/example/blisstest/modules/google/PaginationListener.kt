package com.example.blisstest.modules.google

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


const val PAGE_START = 1
private const val PAGE_SIZE = 10
abstract class PaginationListener: RecyclerView.OnScrollListener() {

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = getLinearLayoutManager().childCount
        val totalItemCount = getLinearLayoutManager().itemCount
        val firstVisibleItemPosition = getLinearLayoutManager().findFirstVisibleItemPosition()
        if (!isLoading() && !isLastPage()) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

    abstract fun getLinearLayoutManager(): LinearLayoutManager


}