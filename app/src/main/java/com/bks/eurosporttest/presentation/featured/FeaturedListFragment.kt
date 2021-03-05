package com.bks.eurosporttest.presentation.featured

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.FragmentFeaturedBinding
import com.bks.eurosporttest.domain.model.Video
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "FeaturedListFragment"

@AndroidEntryPoint
class FeaturedListFragment: Fragment(R.layout.fragment_featured), FeaturedAdapter.Interaction {

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
                featuredAdapter = FeaturedAdapter(
                    this@FeaturedListFragment
                )
                adapter = featuredAdapter
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.videos.observe(viewLifecycleOwner) {
            featuredAdapter.updateItems(it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            displayProgress(it)
        }
    }

    private fun displayProgress(isDisplayed: Boolean) {
        if(isDisplayed) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onItemSelected(position: Int, item: Video) {
        Log.d(TAG, "onItemSelected: ${item.title} selected")
    }
}