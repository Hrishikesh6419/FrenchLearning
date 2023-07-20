package com.example.frenchlearning.words.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frenchlearning.data.Word
import com.example.frenchlearning.databinding.ItemWordBinding

class WordsAdapter(private val onWordClick: (Word) -> Unit) :
    ListAdapter<Word, WordsAdapter.WordViewHolder>(WordDiffCallback()) {

    class WordViewHolder(
        private val binding: ItemWordBinding,
        private val onWordClick: (Word) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.englishWord.text = word.englishWord
            binding.root.setOnClickListener { onWordClick(word) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val binding = ItemWordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WordViewHolder(binding, onWordClick)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class WordDiffCallback : DiffUtil.ItemCallback<Word>() {
    override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem.englishWord == newItem.englishWord
    }

    override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
        return oldItem == newItem
    }
}
