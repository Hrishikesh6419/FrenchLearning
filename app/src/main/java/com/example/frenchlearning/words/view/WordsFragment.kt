package com.example.frenchlearning.words.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frenchlearning.R
import com.example.frenchlearning.data.Word
import com.example.frenchlearning.databinding.FragmentWordsBinding
import com.example.frenchlearning.words.WordsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : Fragment() {

    private val viewModel: WordsViewModel by viewModels()

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeWords()
        observeLoading()
        setListeners()
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadWords(isRefresh = true)
        }
    }

    private fun setAdapter() {
        adapter = WordsAdapter { word: Word ->
            val action = WordsFragmentDirections.actionNavWordsToWordDetailFragment(word)
            findNavController().navigate(action)
        }
        binding.viewWords.adapter = adapter
        binding.viewWords.layoutManager = LinearLayoutManager(context) // Set the layout manager
    }

    private fun observeWords() {
        viewModel.words.observe(viewLifecycleOwner) { words ->
            binding.swipeRefreshLayout.isRefreshing = false
            if (words.isEmpty()) {
                Toast.makeText(context, getString(R.string.no_words_available), Toast.LENGTH_LONG)
                    .show()
            } else {
                adapter.submitList(words)
            }
        }
    }

    private fun observeLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}