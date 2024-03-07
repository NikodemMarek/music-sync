package com.example.musicsync.fragments

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicsync.data.Track
import kotlin.collections.ArrayList

class FetchViewModel : ViewModel() {
    private val tracksList: ArrayList<Track> = ArrayList()
    private val observedItemList: MutableLiveData<ArrayList<Track>> = MutableLiveData()

    init {
        tracksList.add(Track("Title 1", "url 1"))
        tracksList.add(Track("Title 2", "url 2"))
        tracksList.add(Track("Title 3", "url 3"))

        observedItemList.value = tracksList
    }

    fun getObservedItemList(): MutableLiveData<ArrayList<Track>> {
        return observedItemList
    }

    fun addTrack() {
        tracksList.add(Track("added", "url"))
        observedItemList.value = tracksList
    }
}
