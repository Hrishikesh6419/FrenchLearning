package com.example.frenchlearning.words

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frenchlearning.network.WordsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WordsViewModel @Inject constructor(
    private val wordsRepository: WordsRepository
) : ViewModel() {

    fun loadWords() {
        viewModelScope.launch(Dispatchers.IO) {
            wordsRepository.fetchWords()
        }
    }
}