package com.example.frenchlearning.statements.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.frenchlearning.R
import com.example.frenchlearning.data.model.Statement
import com.example.frenchlearning.databinding.FragmentStatementsBinding
import com.example.frenchlearning.statements.StatementsAdapter
import com.example.frenchlearning.statements.StatementsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatementsFragment : Fragment() {

    private val viewModel: StatementsViewModel by viewModels()

    private var _binding: FragmentStatementsBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: StatementsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatementsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        observeStatements()
        observeLoading()
        setListeners()
    }

    private fun setListeners() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadStatements(isRefresh = true)
        }
    }

    private fun setAdapter() {
        adapter = StatementsAdapter(shouldShowFrench = false) { statement: Statement ->
//            val action = WordsFragmentDirections.actionNavWordsToWordDetailFragment(statement)
//            findNavController().navigate(action)
            Toast.makeText(context, "Clicked", Toast.LENGTH_LONG)
                .show()
        }
        binding.viewStatements.adapter = adapter
        binding.viewStatements.layoutManager =
            LinearLayoutManager(context) // Set the layout manager
    }

    private fun observeStatements() {
        viewModel.statements.observe(viewLifecycleOwner) { statements ->
            binding.swipeRefreshLayout.isRefreshing = false
            if (statements.isEmpty()) {
                Toast.makeText(
                    context,
                    getString(R.string.no_statements_available),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                adapter.submitList(statements)
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