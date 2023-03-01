package ru.madmax.vktestapp.presentation.detailsScreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import ru.madmax.vktestapp.databinding.FragmentDetailsBinding

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<DetailsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.eventFlow.collectLatest { message ->
                Toast
                    .makeText(requireContext(), message, Toast.LENGTH_SHORT)
                    .show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenResumed {
            viewModel.uiState.collectLatest { state ->
                binding.apply {
                    Glide
                        .with(requireContext())
                        .load(state.gifUrl)
                        .into(image)

                    title.text = state.title
                    Glide
                        .with(requireContext())
                        .load(state.userAvatarUrl)
                        .into(userAvatar)

                    username.text = state.username

                    if (state.isLoading) {
                        binding.progress.visibility = View.VISIBLE
                        binding.content.visibility = View.INVISIBLE
                    } else {
                        binding.progress.visibility = View.GONE
                        binding.content.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}