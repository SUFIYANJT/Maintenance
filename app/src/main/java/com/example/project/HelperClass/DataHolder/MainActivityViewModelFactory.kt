package com.example.project.HelperClass.DataHolder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project.HelperClass.Downloads.ResponseActivity

class MainActivityViewModel: ViewModel() {
    // Create a MutableLiveData to hold a string
    private val _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String> = _textLiveData

    // Function to update the LiveData
    fun updateText(newText: String) {
        _textLiveData.value = newText
    }
}

