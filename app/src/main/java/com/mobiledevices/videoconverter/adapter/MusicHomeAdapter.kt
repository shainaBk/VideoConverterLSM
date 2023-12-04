package com.mobiledevices.videoconverter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R

class MusicHomeAdapter(
    private val musicList: MutableList<Music>,
    private val downloadListener: OnMusicDownloadListener
) : RecyclerView.Adapter<MusicHomeAdapter.MusicHomeViewHolder>() {

    /**
     * A view holder for the recycler view: it contains the view elements
     */
    class MusicHomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val artist: TextView = itemView.findViewById(R.id.tv_artist)
        val download: ImageButton = itemView.findViewById(R.id.btn_download)
    }

    /**
     * A diff callback to update the recycler view adapter: allow animations + better performance
     * @param oldList The old music list
     * @param newList The new music list
     */
    class MusicDiffCallback(
        private val oldList: List<Music>,
        private val newList: List<Music>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size
        override fun getNewListSize(): Int = newList.size
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].videoId == newList[newItemPosition].videoId

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }

    /**
     * Create the view holder
     * @param parent The parent view
     * @param viewType The view type: not used
     * @return The view holder
     */
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicHomeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_research, parent, false)
        return MusicHomeViewHolder(itemView)
    }

    /**
     * Bind the view holder with the music data
     * @param holder The view holder
     * @param position The position of the music in the list
     */
    override fun onBindViewHolder(holder: MusicHomeViewHolder, position: Int) {
        val music = musicList[position]

        holder.thumbnail.setImageResource(R.drawable.ic_launcher_background)
        holder.title.text = music.title
        holder.artist.text = music.channelTitle

        holder.download.setOnClickListener {
            Snackbar
                .make(it, "Downloading ${music.title}...", Snackbar.LENGTH_SHORT)
                .setAction("Close") {}
                .show()
            downloadMusic(music.videoId)
        }
    }

    /**
     * Get the number of music in the list
     * @return The number of music
     */
    override fun getItemCount() = musicList.size

    /**
     * Update the music list and notify the recycler view adapter
     * @param newMusicList The new music list
     */
    fun updateMusicList(newMusicList: List<Music>) {
        val diffCallback = MusicDiffCallback(musicList, newMusicList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        musicList.clear()
        musicList.addAll(newMusicList)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Download a music: transfer it to the library
     * @param musicId The music id
     */
    private fun downloadMusic(musicId: String) {
        val music = musicList.find { it.videoId == musicId }
        music?.let {
            it.isDownloaded = true
            downloadListener.onMusicDownload(it)
        }
    }

    /**
     * Interface to handle the click on the download button
     */
    interface OnMusicDownloadListener {
        fun onMusicDownload(music: Music)
    }
}