package com.example.blisstest.presentation.ui.common.list_holder

import android.util.Log

class ListHolder<T>(val list: MutableList<T> = mutableListOf()) {
    var changedItems = mutableListOf<ChangedItem>()
    var type: ListHolderType = ListHolderType.FULL

    fun resetList(newList: List<T>) {
        list.clear()
        list.addAll(newList)

        type = ListHolderType.FULL
    }

//    fun updateItem(indexOfItem: (List<T>) -> Int, update: (T) -> Unit): Boolean {
//        val indexOf = indexOfItem.invoke(list)
//
//        if (indexOf == -1) return false
//
//        update.invoke(list[indexOf])
//
//        changedItems.add(ChangedItem(indexOf, ChangedItemType.UPDATE))
//        type = ListHolderType.PARTIAL
//
//        return true
//    }

    fun clearChangedItems() {
        changedItems.clear()
    }

    fun removeItem(position: Int) {
        list.removeAt(position)

        changedItems.add(ChangedItem(position, ChangedItemType.REMOVE))
        type = ListHolderType.PARTIAL
    }

    fun get(index: Int): T = list[index]
}