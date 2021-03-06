package com.bks.eurosporttest.presentation.player

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bks.eurosporttest.databinding.FragmentPlayerBinding
import com.bks.eurosporttest.domain.model.Video
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util

private const val TAG = "PlayerFragment"

class PlayerFragment: Fragment() {

    lateinit var player: SimpleExoPlayer
    lateinit var binding: FragmentPlayerBinding
    var mediaUrl = ""

    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSelectedVideo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)

        if (savedInstanceState != null) {
            playWhenReady = savedInstanceState.getBoolean(STATE_KEY_AUTO_PLAY);
            currentWindow = savedInstanceState.getInt(STATE_KEY_WINDOW);
            playbackPosition = savedInstanceState.getLong(STATE_KEY_POSITION);
        }

        return binding.root
    }



    private fun getSelectedVideo() {
        arguments?.let { args ->
            args.getParcelable<Video>(SELECTED_VIDEO_BUNDLE_KEY)?.let {
                mediaUrl = it.url
            }
        }
    }

    private fun initPlayer() {
        player = SimpleExoPlayer.Builder(requireContext()).build()

        val mediaItem: MediaItem = MediaItem.fromUri(mediaUrl)
        player.setMediaItem(mediaItem)

        player.setPlayWhenReady(playWhenReady)
        player.seekTo(currentWindow, playbackPosition)
        player.prepare()

        binding.videoView.player = player
    }


    private fun hideSystemUi() {
        // use WindowInsetsController instead
        binding.videoView.setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LOW_PROFILE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        )
    }


    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        hideSystemUi()
        if (Util.SDK_INT < 24) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Build.VERSION.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private fun releasePlayer() {
        try {
            playWhenReady = player.getPlayWhenReady()
            playbackPosition = player.getCurrentPosition()
            currentWindow = player.getCurrentWindowIndex()
            player.release()
        } catch (e: Exception) {
            Log.e(TAG, "Error release player: ${e.printStackTrace()}")
        }
    }

    private fun updateStartPosition() {
        playWhenReady = player.playWhenReady
        currentWindow = player.currentWindowIndex
        playbackPosition = Math.max(0, player.contentPosition)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        updateStartPosition()
        outState.putBoolean(STATE_KEY_AUTO_PLAY, playWhenReady)
        outState.putInt(STATE_KEY_WINDOW, currentWindow)
        outState.putLong(STATE_KEY_POSITION, playbackPosition)
    }

}