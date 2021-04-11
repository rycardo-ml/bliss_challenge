package com.example.blisstest.modules.emoji

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blisstest.databinding.EmojiFragmentBinding
import com.example.blisstest.util.ui.list.adapter.ListItemAdapter
import com.example.blisstest.util.data.model.Emoji

class EmojiFragment : Fragment() {

    companion object {
        fun newInstance() = EmojiFragment()
    }

    private var _binding: EmojiFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListItemAdapter<Emoji>

    private lateinit var viewModel: EmojiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = EmojiFragmentBinding.inflate(inflater, container, false)

        adapter = ListItemAdapter { removeItem(it) }
        binding.frgEmojiRv.layoutManager = GridLayoutManager(context, 4)
        binding.frgEmojiRv.adapter = adapter

        configureSwipeRefresh()

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(EmojiViewModel::class.java)
        registerObservers()

        viewModel.fetchEmojis()
    }

    override fun onDestroyView() {
        unregisterObservers()

        _binding = null
        super.onDestroyView()
    }

    private fun removeItem(position: Int) {
        adapter.removeItem(position)
    }

    private fun configureSwipeRefresh() {
        binding.root.setOnRefreshListener { viewModel.fetchEmojis() }
        binding.root.setColorSchemeResources(
            com.example.blisstest.R.color.colorPrimary,
            R.color.holo_green_dark,
            R.color.holo_orange_dark,
            R.color.holo_blue_dark
        )
    }

    private fun registerObservers() {
        viewModel.getEmojis().observe(viewLifecycleOwner, {
            adapter.refreshData(it)
            binding.root.isRefreshing = false
        })
    }

    private fun unregisterObservers() {
        viewModel.getEmojis().removeObservers(viewLifecycleOwner)
    }
}