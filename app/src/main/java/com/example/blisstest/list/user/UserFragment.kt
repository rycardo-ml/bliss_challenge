package com.example.blisstest.list.emoji

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blisstest.databinding.UserFragmentBinding
import com.example.blisstest.list.adapter.ListItemAdapter
import com.example.blisstest.util.ListHolderType
import com.example.blisstest.util.data.model.User

class UserFragment : Fragment() {

    companion object {
        fun newInstance() = UserFragment()
    }

    private var _binding: UserFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListItemAdapter<User>

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = UserFragmentBinding.inflate(inflater, container, false)

        adapter = ListItemAdapter { removeItem(it) }
        binding.frgUserRv.layoutManager = GridLayoutManager(context, 3)
        binding.frgUserRv.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        registerObservers()

        viewModel.fetchUsers()
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