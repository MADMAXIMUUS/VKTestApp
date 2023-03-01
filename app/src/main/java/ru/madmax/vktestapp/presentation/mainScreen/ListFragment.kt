package ru.madmax.vktestapp.presentation.mainScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.madmax.vktestapp.databinding.FragmentListBinding
import ru.madmax.vktestapp.util.GIfItemDecoration
import ru.madmax.vktestapp.util.hideKeyboard

@AndroidEntryPoint
class ListFragment : Fragment(), GifsAdapter.OnClickListener {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListViewModel>()

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = view.findNavController()

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.eventFlow.collectLatest { message ->
                Toast
                    .makeText(requireContext(), message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        val gifsAdapter = GifsAdapter(this)
        val layoutManager = LinearLayoutManager(requireContext())
        val scrollListener = object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (!viewModel.uiState.value.isLoading) {
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.uiState.value.list.size - 1) {
                        viewModel.loadNext()
                    }
                }
            }
        }

        binding.gifsRv.apply {
            adapter = gifsAdapter
            this.layoutManager = layoutManager
            addItemDecoration(GIfItemDecoration(20, 40))
        }
        binding.searchBar.addTextChangedListener(afterTextChanged = { text ->
            viewModel.updateSearchBarState(text.toString())
        })

        binding.searchBar.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.search()
                requireActivity().hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.uiState.collectLatest { state ->
                if (state.isLoading) {
                    binding.progress.visibility = View.VISIBLE
                    binding.gifsRv.visibility = View.INVISIBLE
                } else {
                    binding.progress.visibility = View.GONE
                    binding.gifsRv.visibility = View.VISIBLE
                    gifsAdapter.submitList(state.list)
                }
                binding.gifsRv.removeOnScrollListener(scrollListener)
                binding.gifsRv.addOnScrollListener(scrollListener)
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(id: String) {
        val action = ListFragmentDirections.actionListFragmentToDetailsFragment(id)
        navController.navigate(action)
    }
}