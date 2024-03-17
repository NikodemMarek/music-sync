package com.example.musicsync.fragments

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicsync.data.Track
import com.example.musicsync.providers.Provider
import kotlinx.coroutines.launch

class AddTrackViewModel : ViewModel() {
    lateinit var provider: Provider

    var title = MutableLiveData<String>()
    var artist = MutableLiveData<String>()
    var album = MutableLiveData<String>()

    init {
        title.value = ""
        artist.value = ""
        album.value = ""
    }

    fun add() {
        if (title.value.isNullOrEmpty()) {
            return
        }

        if (artist.value.isNullOrEmpty()) {
            return
        }

        viewModelScope.launch {
            try {
                val album =
                    if (album.value.isNullOrEmpty()) {
                        "-"
                    } else {
                        album.value!!
                    }

                val track =
                    Track(
                        title.value ?: "",
                        artist.value ?: "",
                        album,
                        "-",
                    )

                provider.addTrack(track)
            } catch (e: Exception) {
                Log.e("xxx: AddTrackViewModel", e.message!!)
            }
        }
    }
}
