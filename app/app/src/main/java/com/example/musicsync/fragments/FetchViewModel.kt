package com.example.musicsync.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicsync.data.Track
import com.example.musicsync.providers.Provider
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class FetchViewModel : ViewModel() {
    lateinit var provider: Provider

    private val observedItemList: MutableLiveData<ArrayList<Track>> = MutableLiveData()

    fun getTracksList() {
        viewModelScope.launch {
            try {
                val tracks = provider.getAllTracks()
                observedItemList.value = ArrayList(tracks)
            } catch (e: Exception) {
                Log.e("xxx", "Error fetching tracks", e)
            }
        }
    }

    fun getObservedItemList(): MutableLiveData<ArrayList<Track>> {
        return observedItemList
    }
}
