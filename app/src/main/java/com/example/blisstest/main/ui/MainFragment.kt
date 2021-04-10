package com.example.blisstest.main.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.blisstest.databinding.LayoutMainEmojiBinding
import com.example.blisstest.databinding.LayoutMainUserBinding
import com.example.blisstest.databinding.MainFragmentBinding
import com.example.blisstest.list.ListActivity
import com.example.blisstest.list.ListType
import com.squareup.picasso.Picasso


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private var _bindingLayoutEmoji: LayoutMainEmojiBinding? = null
    private val bindingLayoutEmoji get() = _bindingLayoutEmoji!!

    private var _bindingLayoutUser: LayoutMainUserBinding? = null
    private val bindingLayoutUser get() = _bindingLayoutUser!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        _bindingLayoutEmoji = LayoutMainEmojiBinding.bind(binding.root)
        _bindingLayoutUser = LayoutMainUserBinding.bind(binding.root)

        bindingLayoutEmoji.lytMainEmojiBtRandom.setOnClickListener { viewModel.fetchRandomEmoji() }
        bindingLayoutEmoji.lytMainEmojiTvList.setOnClickListener { openList(ListType.EMOJI) }

        bindingLayoutUser.lytMainUserIvSearch.setOnClickListener { searchUser() }
        bindingLayoutUser.lytMainUserTvList.setOnClickListener { openList(ListType.USER) }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        registerObservers()
    }

    override fun onDestroyView() {
        unregisterObservers()

        _bindingLayoutUser = null
        _bindingLayoutEmoji = null
        _binding = null

        super.onDestroyView()
    }

    private fun searchUser() {
        val userToSearch = bindingLayoutUser.lytMainUserEt.text.toString()
        viewModel.fetchUser(userToSearch)
    }

    private fun openList(type: ListType) {
        val intent = Intent(activity, ListActivity::class.java)
        intent.putExtras(ListActivity.createBundle(type))

        startActivity(intent)
    }

    private fun registerObservers() {

        viewModel.getRandomEmoji().observe(viewLifecycleOwner, {
            if (it == null) return@observe

            bindingLayoutEmoji.lytMainEmojiRowEmoji.rowEmojiTv.text = it.description
            Picasso.get().load(it.url).into(bindingLayoutEmoji.lytMainEmojiRowEmoji.rowEmojiIv)
        })
    }

    private fun unregisterObservers() {
        viewModel.getRandomEmoji().removeObservers(viewLifecycleOwner)
    }
}
