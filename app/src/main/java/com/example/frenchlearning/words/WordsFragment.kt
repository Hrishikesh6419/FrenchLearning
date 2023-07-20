package com.example.frenchlearning.words

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.frenchlearning.databinding.FragmentWordsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WordsFragment : Fragment() {

    private val viewModel: WordsViewModel by viewModels()

    private var _binding: FragmentWordsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeWords()
    }

    private fun observeWords() {
        viewModel.words.observe(this) { words ->
            if (words.isEmpty()) {
                Toast.makeText(context, "No Words available", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Words Loaded", Toast.LENGTH_LONG).show()
                Log.d("hrishiii", "observeWords: $words")
                // Update your UI with the words
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}