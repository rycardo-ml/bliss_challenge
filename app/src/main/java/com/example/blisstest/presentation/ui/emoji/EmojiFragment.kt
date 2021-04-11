package com.example.blisstest.presentation.ui.emoji

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.blisstest.databinding.EmojiFragmentBinding
import com.example.blisstest.presentation.ui.common.list_items.adapter.ListItemAdapter
import com.example.blisstest.util.model.Emoji
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmojiFragment : Fragment() {

    companion object {
        fun newInstance() = EmojiFragment()
    }

    private var _binding: EmojiFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ListItemAdapter<Emoji>

    private val viewModel by viewModels<EmojiViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = EmojiFragmentBinding.inflate(inflater, container, false)

        adapter = ListItemAdapter { removeItem(it) }
        binding.frgEmojiRv.layoutManager = GridLayoutManager(context, 4)
        binding.frgEmojiRv.adapter = adapter

        configureSwipeRefresh()

        registerObservers()
        viewModel.fetchEmojis()

        return binding.root
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
        binding.root.setOnRefreshListener {
            viewModel.fetchEmojis()
        }
        binding.root.setColorSchemeResources(
            com.example.blisstest.R.color.colorPrimary,
            R.color.holo_green_dark,
            R.color.holo_orange_dark,
            R.color.holo_blue_dark
        )
    }

    private fun registerObservers() {
        viewModel.emojis.observe(viewLifecycleOwner, { result ->
            result ?: return@observe

            adapter.refreshData(result.data)
            binding.root.isRefreshing = false
        })
    }

    private fun unregisterObservers() {
        viewModel.emojis.removeObservers(viewLifecycleOwner)
    }
}