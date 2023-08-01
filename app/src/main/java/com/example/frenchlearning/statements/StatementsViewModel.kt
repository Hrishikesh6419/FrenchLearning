package com.example.frenchlearning.statements

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.frenchlearning.data.model.Statement
import com.example.frenchlearning.data.repository.StatementsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class StatementsViewModel @Inject constructor(
    private val statementsRepository: StatementsRepository
) : ViewModel() {

    private val _statements = MutableLiveData<List<Statement>>()
    val statements: LiveData<List<Statement>> = _statements

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        loadStatements()
    }

    fun loadStatements(isRefresh: Boolean = false) {
        if (!isRefresh) {
            _isLoading.value = true
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("hrishiiii", "loadStatements: Statements being fetched")
            val statementList = statementsRepository.fetchStatementsSheet()
            // The result of repository is posted to the LiveData object on the main thread using withContext(Dispatchers.Main)
            withContext(Dispatchers.Main) {
                _statements.value = statementList
                _isLoading.value = false
            }
        }
    }
}