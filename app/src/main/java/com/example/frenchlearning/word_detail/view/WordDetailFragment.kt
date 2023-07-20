package com.example.frenchlearning.word_detail.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.frenchlearning.R
import com.example.frenchlearning.databinding.FragmentWordDetailBinding
import com.example.frenchlearning.util.CommonUtil
import com.example.frenchlearning.word_detail.WordDetailViewModel
import java.util.*

class WordDetailFragment : Fragment() {

    private var _binding: FragmentWordDetailBinding? = null
    private var textToSpeech: TextToSpeech? = null

    private val binding get() = _binding!!
    private val viewModel: WordDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWordDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeWord()
        setHeadingPronunciationListener()
    }

    private fun setHeadingPronunciationListener() {
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech?.language = Locale.FRENCH
            }
        }

        binding.layoutPronunciation.setOnClickListener {
            val word = viewModel.word.value
            if (word != null) {
                textToSpeech?.speak(word.frenchWord, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    private fun observeWord() {
        viewModel.word.observe(viewLifecycleOwner) { word ->
            binding.englishWord.setCustomText(word.englishWord)
            binding.frenchWord.setCustomText(word.frenchWord)
            binding.pronunciation.setCustomText(word.pronunciation)
            binding.relatedWords.setCustomText(word.relatedWords)
            binding.explanation.setCustomText(word.explanation)
            binding.mnemonics.setCustomText(word.mnemonics)
            binding.category.text = resources.getString(CommonUtil.getCategory(word.category))
        }
    }

    private fun TextView.setCustomText(text: String?) {
        if (!text.isNullOrEmpty()) {
            this.text = text
        } else {
            this.text = resources.getString(R.string.not_available)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        textToSpeech?.stop()
        textToSpeech?.shutdown()
        textToSpeech = null
        _binding = null
    }
}