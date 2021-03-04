package com.bks.eurosporttest.presentation.featured

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.FragmentFeaturedBinding
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FeaturedListFragment"

@AndroidEntryPoint
class FeaturedListFragment: Fragment(R.layout.fragment_featured) {

    private val viewModel: FeaturedListViewModel by viewModels()

    lateinit var binding: FragmentFeaturedBinding
    lateinit var featuredAdapter: FeaturedAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFeaturedBinding.bind(view)

        setupViews()
        subscribeObservers()
    }

    private fun setupViews() {
        binding.apply {
            recyclerViewFeatured.apply {
                layoutManager = LinearLayoutManager(requireContext())
                featuredAdapter = FeaturedAdapter()
                adapter = featuredAdapter
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.videos.observe(viewLifecycleOwner) {
            Log.d(TAG, "subscribeObservers: list to submit: $it")
            featuredAdapter.submitList(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            //show or hide progressBar
        }
    }


}