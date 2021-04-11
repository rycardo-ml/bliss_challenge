package com.example.blisstest.modules.google

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blisstest.databinding.GoogleReposFragmentBinding
import com.example.blisstest.modules.google.adapter.GoogleAdapter2


class GoogleReposFragment : Fragment() {

    companion object {
        fun newInstance() = GoogleReposFragment()
    }

    private var _binding: GoogleReposFragmentBinding? = null
    private val binding get() = _binding!!

    private val adapter = GoogleAdapter2()

    private lateinit var viewModel: GoogleReposViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = GoogleReposFragmentBinding.inflate(inflater, container, false)

        binding.frgGooglereposRv.layoutManager = LinearLayoutManager(context)
        binding.frgGooglereposRv.adapter = adapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(GoogleReposViewModel::class.java)

        registerObservers()
    }

    override fun onDestroyView() {
        unregisterObservers()

        _binding = null
        super.onDestroyView()
    }

    private fun registerObservers() {
        viewModel.searchPagedListLiveData.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun unregisterObservers() {
        viewModel.searchPagedListLiveData.removeObservers(viewLifecycleOwner)
    }
}