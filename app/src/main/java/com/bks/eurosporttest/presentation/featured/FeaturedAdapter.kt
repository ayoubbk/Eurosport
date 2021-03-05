package com.bks.eurosporttest.presentation.featured

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.VideoItemLayoutBinding
import com.bks.eurosporttest.domain.model.Video

/**
 * Use DiffUtil for better performance
 */

class FeaturedAdapter(
    private val interaction: Interaction? = null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val videoItems: ArrayList<Video> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var binding = VideoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VideoViewHolder(binding, interaction)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> {
                holder.bind(videoItems[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return videoItems.size
    }

    fun updateItems(videoList: List<Video>) {
        videoItems.clear()
        videoItems.addAll(videoList)
        notifyDataSetChanged()
    }

    class VideoViewHolder constructor(
        private val itemBinding: VideoItemLayoutBinding,
        private val listener: Listener?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var video: Video
        init {
            itemBinding.cardVideo.setOnClickListener {
                listener?.onItemSelected(adapterPosition, video)
            }
        }

        fun bind(item: Video) {
            video = item
            itemBinding.apply {
                ivThumb.load(item.thumb) {
                    placeholder(R.color.color_box_background)
                    crossfade(true)
                }
                tvTitle.text = item.title
                tvViews.text = item.views.toString().plus(" views")
            }
        }

        interface Listener {
            fun onItemSelected(position: Int, item: Video)
        }
    }


    // Interaction interface implements ViewHolders interfaces
    // Pros: expose only one interface
    interface Interaction: VideoViewHolder.Listener {
        override fun onItemSelected(position: Int, item: Video)
    }

}