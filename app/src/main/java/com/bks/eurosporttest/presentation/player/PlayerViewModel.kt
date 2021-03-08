package com.bks.eurosporttest.presentation.player

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.bks.eurosporttest.domain.model.Video
import com.bks.eurosporttest.presentation.BaseApplication
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

const val SELECTED_VIDEO_BUNDLE_KEY = "selectedVideo"

const val STATE_KEY_WINDOW = "window"
const val STATE_KEY_POSITION = "position"
const val STATE_KEY_AUTO_PLAY = "auto_play"

@HiltViewModel
class PlayerViewModel
@Inject
constructor(
    private val appContext: BaseApplication,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private var _video = MutableLiveData<Video>()
    val video: LiveData<Video> get() = _video

    lateinit var player: SimpleExoPlayer

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition = 0L

    init {
        savedStateHandle.get<Boolean>(STATE_KEY_AUTO_PLAY)?.let {
            setAutoPlay(it)
        }
        savedStateHandle.get<Int>(STATE_KEY_WINDOW)?.let {
            setWindow(it)
        }
        savedStateHandle.get<Long>(STATE_KEY_POSITION)?.let {
            setPosition(it)
        }
    }

    private fun setPosition(position: Long) {
        this.playbackPosition = position
        savedStateHandle.set(STATE_KEY_POSITION, position)
    }

    private fun setWindow(window: Int) {
        this.currentWindow = window
        savedStateHandle.set(STATE_KEY_WINDOW, window)
    }

    private fun setAutoPlay(autoPlay: Boolean) {
        this.playWhenReady = autoPlay
        savedStateHandle.set(STATE_KEY_AUTO_PLAY, autoPlay)
    }

    fun initPlayer(video: Video) {
        _video.value = video

        player = SimpleExoPlayer.Builder(appContext).build()
        val mediaItem: MediaItem = MediaItem.fromUri(video.url)
        player.setMediaItem(mediaItem)
        player.setPlayWhenReady(playWhenReady)
        player.seekTo(currentWindow, playbackPosition)
        player.prepare()
    }

    fun releasePlayer() {
        player.release()
    }

}