package com.example.blisstest.presentation.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blisstest.databinding.UserFragmentBinding
import com.example.blisstest.presentation.ui.common.list_items.adapter.ListItemAdapter
import com.example.blisstest.presentation.ui.common.list_holder.ListHolderType
import com.example.blisstest.util.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListItemAdapter<User>

    private val viewModel by viewModels<UserViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = UserFragmentBinding.inflate(inflater, container, false)

        adapter = ListItemAdapter { removeItem(it) }
        binding.frgUserRv.layoutManager = GridLayoutManager(context, 3)
        binding.frgUserRv.adapter = adapter

        registerObservers()

        return binding.root
    }

    override fun onDestroyView() {
        unregisterObservers()

        _binding = null
        super.onDestroyView()
    }

    private fun removeItem(position: Int) {
        viewModel.deleteUser(position)
    }

    private fun registerObservers() {
        viewModel.getUsers().observe(viewLifecycleOwner, {

            if (it.type == ListHolderType.FULL) {
                adapter.refreshData(it.list)
                return@observe
            }

            adapter.updateChangedItems(it.changedItems)
        })
    }

    private fun unregisterObservers() {
        viewModel.getUsers().removeObservers(viewLifecycleOwner)
    }
}