package com.mobiledevices.videoconverter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mobiledevices.videoconverter.Model.Music
import com.mobiledevices.videoconverter.R

class MusicHomeAdapter(val musicList: MutableList<Music>) :
    RecyclerView.Adapter<MusicHomeAdapter.MusicHomeViewHolder>() {

    class MusicHomeViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val thumbnail: ImageView = view.findViewById(R.id.iv_thumbnail)
        val title: TextView = view.findViewById(R.id.tv_title)
        val artist: TextView = view.findViewById(R.id.tv_artist)
        val download: ImageButton = view.findViewById(R.id.btn_download)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MusicHomeViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_research, parent, false)
        return MusicHomeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicHomeViewHolder, position: Int) {
        val music = musicList[position]

        holder.thumbnail.setImageResource(R.drawable.ic_launcher_background)
        holder.title.text = music.title
        holder.artist.text = music.channel_title

        holder.download.setOnClickListener {
            Snackbar
                .make(it, "Downloading ${music.title}...", Snackbar.LENGTH_SHORT)
                .setAction("Close") {}
                .show()
        }
    }

    override fun getItemCount() = musicList.size

    fun addMusic(music: Music) {
        musicList.add(music)
        notifyItemInserted(musicList.size - 1)
    }
}