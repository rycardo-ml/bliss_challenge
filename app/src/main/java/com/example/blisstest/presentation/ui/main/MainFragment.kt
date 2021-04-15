package com.example.blisstest.presentation.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.blisstest.databinding.LayoutMainEmojiBinding
import com.example.blisstest.databinding.LayoutMainUserBinding
import com.example.blisstest.databinding.MainFragmentBinding
import com.example.blisstest.presentation.ListActivity
import com.example.blisstest.presentation.ui.common.list_items.ListType
import com.example.blisstest.util.Resource
import com.example.blisstest.util.model.User
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainFragment"

@AndroidEntryPoint
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

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        _bindingLayoutEmoji = LayoutMainEmojiBinding.bind(binding.frgMainLytEmoji.root)
        _bindingLayoutUser = LayoutMainUserBinding.bind(binding.frgMainLytUser.root)

        binding.frgMainBtRepositories.setOnClickListener { openList(ListType.GOOGLE_REPOS) }

        bindingLayoutEmoji.lytMainEmojiBtRandom.setOnClickListener { viewModel.fetchRandomEmoji() }
        bindingLayoutEmoji.lytMainEmojiTvList.setOnClickListener { openList(ListType.EMOJI) }

        bindingLayoutUser.lytMainUserEt.setOnEditorActionListener { v, actionId, event -> handleEditTextAction(
            actionId
        )  }
        bindingLayoutUser.lytMainUserIvSearch.setOnClickListener { searchUser() }
        bindingLayoutUser.lytMainUserTvList.setOnClickListener { openList(ListType.USER) }

        registerObservers()

        return binding.root
    }

    override fun onDestroyView() {
        unregisterObservers()

        _bindingLayoutUser = null
        _bindingLayoutEmoji = null
        _binding = null

        super.onDestroyView()
    }

    private fun handleEditTextAction(actionId: Int): Boolean {
        return when (actionId) {
            EditorInfo.IME_ACTION_SEARCH -> {
                searchUser()
                true
            }
            else -> false
        }
    }

    private fun searchUser() {
        showUserLoadingView()
        hideKeyboard()

        val userToSearch = bindingLayoutUser.lytMainUserEt.text.toString()
        viewModel.fetchUser(userToSearch)
    }

    private fun openList(type: ListType) {
        val intent = Intent(activity, ListActivity::class.java)
        intent.putExtras(ListActivity.createBundle(type))

        startActivity(intent)
    }

    private fun handleUserFetched(item: Resource<User?>) {
        Log.d(TAG, "user fetched ${item.fetchedFromApi}")

        if (item is Resource.Error) {
            bindingLayoutUser.lytMainUserTvStatus.text = "failed to fetch user ${item.error?.message}"
            bindingLayoutUser.lytMainUserTvStatus.visibility = View.VISIBLE
            return
        }

        if (item is Resource.Loading) {
            showUserLoadingView()
            return
        }

        val successMessage =
            if (item.fetchedFromApi) "User ${item.data?.getDescriptionText()} fetched from api."
            else "User ${item.data?.getDescriptionText()} already fetched."

        bindingLayoutUser.lytMainUserTvStatus.text = successMessage
        bindingLayoutUser.lytMainUserTvStatus.visibility = View.VISIBLE
    }

    private fun showUserLoadingView() {
        bindingLayoutUser.layoutLoading.run {
            visibility = View.VISIBLE
            bringToFront()
        }

        bindingLayoutUser.lytMainUserTvStatus.visibility = View.GONE
    }

    private fun hideUserLoadingView() {
        bindingLayoutUser.run {
            layoutLoading.visibility = View.GONE
            lytMainUserEt.requestFocus()
        }
    }

    private fun hideKeyboard() {
        (context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).run {
            hideSoftInputFromWindow(bindingLayoutUser.lytMainUserEt.windowToken, 0)
        }
    }

    private fun registerObservers() {

        viewModel.randomEmoji.observe(viewLifecycleOwner, {
            if (it == null) return@observe

            it.data?.let {
                bindingLayoutEmoji.lytMainEmojiRowEmoji.rowEmojiTv.text = it.description
                Picasso.get().load(it.url).into(bindingLayoutEmoji.lytMainEmojiRowEmoji.rowEmojiIv)
            }
        })

        viewModel.fetchedUser.observe(viewLifecycleOwner, {
            if (it == null) return@observe

            hideUserLoadingView()
            handleUserFetched(it)
        })
    }

    private fun unregisterObservers() {
        viewModel.randomEmoji.removeObservers(viewLifecycleOwner)
        viewModel.fetchedUser.removeObservers(viewLifecycleOwner)
    }
}
