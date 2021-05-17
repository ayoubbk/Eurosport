package com.bks.eurosporttest.presentation.storydetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bks.eurosporttest.domain.model.Story

const val SELECTED_STORY_BUNDLE_KEY = "selectedStory"

class StoryViewModel: ViewModel() {

    private var _story = MutableLiveData<Story>()
    val story: LiveData<Story> get() = _story

    fun initStory(story: Story) {
        _story.value = story
    }


}