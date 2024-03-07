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
                binding.fetchListItemName.text = name
                binding.fetchListItemUrl.text = url
            }
        }
    }

    override fun getItemCount(): Int = data.size
}
