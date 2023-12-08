package com.mobiledevices.videoconverter.Ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R

class MusicLibraryAdapter(
    private val musicList: MutableList<Music>
) : RecyclerView.Adapter<MusicLibraryAdapter.MusicLibraryViewHolder>() {

    /**
     * A view holder for the recycler view: it contains the view elements
     */
    class MusicLibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val artist: TextView = itemView.findViewById(R.id.tv_artist)
        val favorite: ImageButton = itemView.findViewById(R.id.btn_favorite)
        val play: ImageButton = itemView.findViewById(R.id.btn_play)
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
    ): MusicLibraryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return MusicLibraryViewHolder(itemView)
    }

    /**
     * Bind the view holder with the music data
     * @param holder The view holder
     * @param position The position in the list
     */
    override fun onBindViewHolder(holder: MusicLibraryViewHolder, position: Int) {
        val music = musicList[position]

        holder.thumbnail.load(music.thumbnailUrl) {
            crossfade(true)
            error(R.drawable.ic_error) // Image d'erreur
            listener(onError = { _, throwable ->
                Log.e("ImageLoadError", "Erreur lors du chargement de l'image: ${throwable}")
            })
        }

        holder.title.text = music.title
        holder.artist.text = music.channelTitle
        updateFavoriteIcon(holder.favorite, music.isFavorite)

        holder.favorite.setOnClickListener {
            music.isFavorite = !music.isFavorite
            updateFavoriteIcon(holder.favorite, music.isFavorite)
        }
    }

    /**
     * Get the number of items in the list
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
     * Update the favorite icon
     * @param favoriteButton The favorite button
     * @param isFavorite The favorite state
     */
    private fun updateFavoriteIcon(favoriteButton: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}