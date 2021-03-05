package com.bks.eurosporttest.presentation.storydetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.FragmentStoryDetailsBinding
import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.util.DateUtils
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StoryFragment : Fragment(R.layout.fragment_story_details) {

    private val viewModel: StoryViewModel by viewModels()

    lateinit var binding: FragmentStoryDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentStoryDetailsBinding.bind(view)

        getSelectedStory()
        subscribeObservers()
    }

    private fun subscribeObservers() {
        viewModel.story.observe(viewLifecycleOwner) {story ->
            // set story
            setupView(story)
        }
    }

    private fun setupView(story: Story) {
        binding.apply {
            ivImage.load(story.image) {
                placeholder(R.color.color_box_background)
                crossfade(true)
            }
            tvTitle.text = story.title
            tvAuthor.text = "By ".plus(story.author)
            tvTime.text = DateUtils.dateToString(story.date)
            tvTeaser.text = story.teaser
        }
    }


    private fun getSelectedStory() {
        arguments?.let { args ->
            (args.getParcelable<Story>(SELECTED_STORY_BUNDLE_KEY))?.let { selectedStory ->
                viewModel.initStory(selectedStory)
            }
        }
    }

}