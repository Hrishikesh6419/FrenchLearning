package com.example.frenchlearning.words

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frenchlearning.data.model.Word
import com.example.frenchlearning.data.repository.WordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>>()
    val words: LiveData<List<Word>> = _words

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadWords()
    }

    fun loadWords(isRefresh: Boolean = false) {
        if (!isRefresh) {
            _isLoading.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("hrishiiii", "loadWords: Words being fetched")
            val wordsList = wordsRepository.fetchWordsSheet()
            // The result of wordsRepository.fetchWords() is posted to
            // the _words LiveData object on the main thread using withContext(Dispatchers.Main)
            withContext(Dispatchers.Main) {
                _words.value = wordsList
                _isLoading.value = false
            }
        }
    }
}