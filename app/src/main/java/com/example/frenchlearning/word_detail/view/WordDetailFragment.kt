package com.example.frenchlearning.word_detail.view

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.frenchlearning.R
import com.example.frenchlearning.databinding.FragmentWordDetailBinding
import com.example.frenchlearning.util.CommonUtil
import com.example.frenchlearning.word_detail.WordDetailViewModel
import java.util.Locale

class WordDetailFragment : Fragment() {

    private var _binding: FragmentWordDetailBinding? = null
    private val binding get() = _binding!!

    private var textToSpeech: TextToSpeech? = null


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
        setPronunciationListener()
        setShowPronunciationListener()
    }

    private fun setPronunciationListener() {
        textToSpeech = TextToSpeech(requireContext()) { status ->
            if (status != TextToSpeech.ERROR) {
                textToSpeech?.language = Locale.FRENCH
            }
        }

        binding.layoutFrenchWord.setOnClickListener {
            val word = viewModel.word.value
            if (word != null) {
                textToSpeech?.speak(word.frenchWord, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    private fun setShowPronunciationListener() {
        binding.showPronunciation.setOnClickListener {
            if (binding.layoutPronunciation.visibility == View.GONE) {
                // Create a fade-in animation
                val fadeIn = AlphaAnimation(0f, 1f)
                fadeIn.duration = 300 // Duration in milliseconds

                binding.layoutPronunciation.startAnimation(fadeIn)
                binding.layoutPronunciation.visibility = View.VISIBLE
                binding.showPronunciation.text = resources.getString(R.string.hide_pronunciation)
            } else {
                // Create a fade-out animation
                val fadeOut = AlphaAnimation(1f, 0f)
                fadeOut.duration = 300 // Duration in milliseconds

                fadeOut.setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation) {}

                    override fun onAnimationEnd(animation: Animation) {
                        binding.layoutPronunciation.visibility = View.GONE
                    }

                    override fun onAnimationRepeat(animation: Animation) {}
                })

                binding.layoutPronunciation.startAnimation(fadeOut)
                binding.showPronunciation.text = resources.getString(R.string.show_pronunciation)
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
            binding.comments.setCustomText(word.comments)
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