package com.bks.eurosporttest.presentation.featured

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bks.eurosporttest.R
import com.bks.eurosporttest.databinding.StoryItemLayoutBinding
import com.bks.eurosporttest.databinding.VideoItemLayoutBinding
import com.bks.eurosporttest.domain.model.Story
import com.bks.eurosporttest.domain.model.Video

/**
 * Use DiffUtil for better performance
 */

class FeaturedAdapter(
    private val interaction: Interaction? = null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items: ArrayList<Any> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when(viewType) {
            R.layout.video_item_layout -> {
                var binding = VideoItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                VideoViewHolder(binding, interaction)
            }

            R.layout.story_item_layout -> {
                var binding = StoryItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                StoryViewHolder(binding, interaction)
            }

            else -> throw RuntimeException("Illegal view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is VideoViewHolder -> holder.bind(items[position] as Video)
            is StoryViewHolder -> holder.bind(items[position] as Story)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is Video -> R.layout.video_item_layout
            is Story -> R.layout.story_item_layout
            else -> throw RuntimeException("Illegal view type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateItems(newList: List<Any>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    class VideoViewHolder constructor(
        private val itemBinding: VideoItemLayoutBinding,
        private val listener: Listener?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var video: Video
        init {
            itemBinding.cardVideo.setOnClickListener {
                listener?.onPlayVideo(adapterPosition, video)
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
            fun onPlayVideo(position: Int, item: Video)
        }
    }

    class StoryViewHolder constructor(
        private val itemBinding: StoryItemLayoutBinding,
        private val listener: Listener?
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        private lateinit var story: Story
        init {
            itemBinding.cardStory.setOnClickListener {
                listener?.onStorySelected(adapterPosition, story)
            }
        }

        fun bind(item: Story) {
            story = item
            itemBinding.apply {
                ivImage.load(item.image) {
                    placeholder(R.color.color_box_background)
                    crossfade(true)
                }
                tvTitle.text = item.title
                tvAuthor.text = "By ".plus(item.author)
                tvDate.text = item.date.toString() // use DateUtils to convert date into ..min ago pattern
            }
        }

        interface Listener {
            fun onStorySelected(position: Int, item: Story)
        }
    }

    // Interaction interface implements ViewHolders interfaces
    // Pros: expose only one interface
    interface Interaction: VideoViewHolder.Listener, StoryViewHolder.Listener {
        override fun onPlayVideo(position: Int, item: Video)
        override fun onStorySelected(position: Int, item: Story)
    }

}