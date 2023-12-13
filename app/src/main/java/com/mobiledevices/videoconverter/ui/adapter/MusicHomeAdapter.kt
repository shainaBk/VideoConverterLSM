package com.mobiledevices.videoconverter.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobiledevices.videoconverter.R
import com.mobiledevices.videoconverter.model.Music


/**
 * Adaptateur pour le RecyclerView dans le fragment Home. Gère l'affichage et les interactions avec
 * la liste des musiques trouvées lors de la recherche.
 */
class MusicHomeAdapter(
    private val musicList: MutableList<Music>,
    private val downloadListener: OnMusicDownloadListener,
    private val onDownloadClicked: (Music) -> Unit
) : RecyclerView.Adapter<MusicHomeAdapter.MusicHomeViewHolder>() {

    companion object {
        private const val TAG = "MusicHomeAdapter"
    }

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
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].videoId == newList[newItemPosition].videoId

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
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

        holder.thumbnail.load(music.thumbnailUrl) {
            crossfade(true)
            error(R.drawable.ic_error) // Image d'erreur
            listener(onError = { _, throwable ->
                Log.e(TAG, "Error loading thumbnail: $throwable")
            })
        }
        holder.title.text = music.title
        holder.artist.text = music.channelTitle

        holder.download.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "Downloading ${music.title}",
                Toast.LENGTH_LONG
            ).show()

            downloadListener.onMusicDownload(music)//MusicViewModel
            onDownloadClicked(music) // ShareViewModel
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
        val diffResult = DiffUtil.calculateDiff(MusicDiffCallback(musicList, newMusicList))
        musicList.clear()
        musicList.addAll(newMusicList)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Interface to handle the click on the download button
     */
    interface OnMusicDownloadListener {
        fun onMusicDownload(music: Music)
    }
}