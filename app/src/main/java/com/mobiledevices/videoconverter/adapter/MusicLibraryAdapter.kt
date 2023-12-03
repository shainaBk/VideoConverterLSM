package com.mobiledevices.videoconverter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R

class MusicLibraryAdapter(private val musicList: MutableList<Music>) :
    RecyclerView.Adapter<MusicLibraryAdapter.MusicLibraryViewHolder>() {

    class MusicLibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.iv_thumbnail)
        val title: TextView = view.findViewById(R.id.tv_title)
        val artist: TextView = view.findViewById(R.id.tv_artist)
        val favorite: ImageButton = view.findViewById(R.id.btn_favorite)
        val play: ImageButton = view.findViewById(R.id.btn_play)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicLibraryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_library, parent, false)
        return MusicLibraryViewHolder(itemView)
    }

    override fun getItemCount() = musicList.size

    override fun onBindViewHolder(holder: MusicLibraryViewHolder, position: Int) {
        val music = musicList[position]

        holder.thumbnail.setImageResource(R.drawable.ic_launcher_background)
        holder.title.text = music.title
        holder.artist.text = music.channel_title

        holder.favorite.setOnClickListener {
            music.isFavorite = !music.isFavorite
            updateFavoriteIcon(holder.favorite, music.isFavorite)
        }
    }

    private fun updateFavoriteIcon(favoriteButton: ImageButton, isFavorite: Boolean) {
        if (isFavorite) {
            favoriteButton.setImageResource(R.drawable.ic_favorite_filled)
        } else {
            favoriteButton.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}