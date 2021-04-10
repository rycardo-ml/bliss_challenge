package com.example.blisstest.main.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blisstest.R
import com.example.blisstest.databinding.LayoutRandomEmojiBinding
import com.example.blisstest.databinding.MainFragmentBinding
import com.squareup.picasso.Picasso

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var _bindingLayoutRandomEmoji: LayoutRandomEmojiBinding? = null
    private val bindingLayoutRandomEmoji get() = _bindingLayoutRandomEmoji!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        _bindingLayoutRandomEmoji = LayoutRandomEmojiBinding.bind(binding.root)

        bindingLayoutRandomEmoji.lytRandomEmojiBtRandom.setOnClickListener { viewModel.fetchRandomEmoji() }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        registerObservers()
    }

    override fun onDestroyView() {
        unregisterObservers()

        _bindingLayoutRandomEmoji = null
        _binding = null
        super.onDestroyView()
    }

    private fun registerObservers() {

        viewModel.getRandomEmoji().observe(viewLifecycleOwner, {
            if (it == null) return@observe

            bindingLayoutRandomEmoji.lytRandomEmojiTvEmoji.text = it.description
            Picasso.get().load(it.url).into(bindingLayoutRandomEmoji.lytRandomEmojiIvEmoji);
        })

    }

    private fun unregisterObservers() {

    }
}