package com.bks.eurosporttest.presentation.storydetail

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.FragmentStoryDetailsBinding
import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.util.EuroDateUtils
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder


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
            val author = StringBuilder()
            author.append(context?.getString(R.string.by_text))
            author.append(" ")
            author.append(story.author)
            tvAuthor.text = author
            tvTime.text = EuroDateUtils.getFormattedDate(story.date)
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