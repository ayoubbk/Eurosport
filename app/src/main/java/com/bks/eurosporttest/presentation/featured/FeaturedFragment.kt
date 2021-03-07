package com.bks.eurosporttest.presentation.featured

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.FragmentFeaturedBinding
import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.domain.model.Video
import com.bks.eurosporttest.presentation.featured.FeaturedViewState.*
import com.bks.eurosporttest.presentation.player.SELECTED_VIDEO_BUNDLE_KEY
import com.bks.eurosporttest.presentation.storydetail.SELECTED_STORY_BUNDLE_KEY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeaturedListFragment: Fragment(R.layout.fragment_featured), FeaturedAdapter.Interaction {

    private val viewModel: FeaturedViewModel by viewModels()

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
        viewModel.videosAndStories.observe(viewLifecycleOwner) { videosAndStories ->
            featuredAdapter.updateItems(videosAndStories)
        }

        viewModel.viewState.observe(viewLifecycleOwner) {  viewState ->
            when(viewState) {
                is Error -> {
                    Snackbar.make(
                        binding.featuredListFragment,
                        viewState.message,
                        Snackbar.LENGTH_LONG
                    ).show()
                }

                is Loading -> {
                    displayProgress(viewState.isLoading)
                }
            }
        }

    }

    private fun displayProgress(isDisplayed: Boolean) {
        if(isDisplayed) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onPlayVideo(position: Int, item: Video) {
        navigateToPlayerScreen(item)
    }

    override fun onStorySelected(position: Int, item: Story) {
        navigateToStoryScreen(item)
    }

    private fun navigateToStoryScreen(selectedStory: Story) {
        val bundle = bundleOf(SELECTED_STORY_BUNDLE_KEY to selectedStory)
        findNavController().navigate(R.id.action_featuredListFragment_to_storyFragment, bundle)
    }

    private fun navigateToPlayerScreen(selectedVideo: Video) {
        val bundle = bundleOf(SELECTED_VIDEO_BUNDLE_KEY to selectedVideo)
        findNavController().navigate(R.id.action_featuredListFragment_to_playerFragment, bundle)
    }
}