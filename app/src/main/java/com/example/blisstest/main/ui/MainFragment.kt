package com.example.blisstest.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.blisstest.databinding.LayoutMainEmojiBinding
import com.example.blisstest.databinding.MainFragmentBinding
import com.example.blisstest.emoji.EmojiActivity
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var _bindingLayoutRandomEmoji: LayoutMainEmojiBinding? = null
    private val bindingLayoutRandomEmoji get() = _bindingLayoutRandomEmoji!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        _bindingLayoutRandomEmoji = LayoutMainEmojiBinding.bind(binding.root)

        bindingLayoutRandomEmoji.lytMainEmojiBtRandom.setOnClickListener { viewModel.fetchRandomEmoji() }
        bindingLayoutRandomEmoji.lytMainEmojiTvList.setOnClickListener { openListOfEmojis() }

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

    private fun openListOfEmojis() {
        val intent = Intent(activity, EmojiActivity::class.java)
        startActivity(intent)
    }

    private fun registerObservers() {

        viewModel.getRandomEmoji().observe(viewLifecycleOwner, {
            if (it == null) return@observe

            bindingLayoutRandomEmoji.lytMainEmojiRowEmoji.rowEmojiTv.text = it.description
            Picasso.get().load(it.url).into(bindingLayoutRandomEmoji.lytMainEmojiRowEmoji.rowEmojiIv)
        })

    }

    private fun unregisterObservers() {
        viewModel.getRandomEmoji().removeObservers(viewLifecycleOwner)
    }
}