package com.example.musicsync.components

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicsync.data.Track
import com.example.musicsync.databinding.FetchListItemBinding

class FetchTrackListAdapter(private val data: List<Track>) : RecyclerView.Adapter<FetchTrackListAdapter.TracksListViewHolder>() {
    inner class TracksListViewHolder(val binding: FetchListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int,
    ): TracksListViewHolder {
        val binding = FetchListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return TracksListViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TracksListViewHolder,
        position: Int,
    ) {
        with(holder) {
            with(data[position]) {
                binding.fetchListItemName.text = "Title: $title"
                binding.fetchListItemArtist.text = "Artist: $artist"
                if (album == "-") {
                    binding.fetchListItemAlbum.text = ""
                } else {
                    binding.fetchListItemAlbum.text = "Album: $album"
                }
                binding.fetchListItemUrl.text = path
            }
        }
    }

    override fun getItemCount(): Int = data.size
}
