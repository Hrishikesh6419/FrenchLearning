package com.example.frenchlearning.statements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.frenchlearning.data.model.Statement
import com.example.frenchlearning.databinding.ItemStatementBinding

class StatementsAdapter(
    private val shouldShowFrench: Boolean,
    private val onStatementClick: (Statement) -> Unit,
) :
    ListAdapter<Statement, StatementsAdapter.StatementViewHolder>(StatementsDiffCallback()) {

    class StatementViewHolder(
        private val binding: ItemStatementBinding,
        private val onStatementClick: (Statement) -> Unit,
        private val shouldShowFrench: Boolean
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(statement: Statement) {
            binding.englishStatement.text =
                if (shouldShowFrench) statement.frenchStatement else statement.englishStatement
            binding.root.setOnClickListener { onStatementClick(statement) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatementViewHolder {
        val binding =
            ItemStatementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatementViewHolder(binding, onStatementClick, shouldShowFrench)
    }

    override fun onBindViewHolder(holder: StatementViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StatementsDiffCallback : DiffUtil.ItemCallback<Statement>() {
    override fun areItemsTheSame(oldItem: Statement, newItem: Statement): Boolean {
        return oldItem.englishStatement == newItem.englishStatement
    }

    override fun areContentsTheSame(oldItem: Statement, newItem: Statement): Boolean {
        return oldItem == newItem
    }
}