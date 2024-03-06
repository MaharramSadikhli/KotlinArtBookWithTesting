package com.imsosoft.kotlinartbookwithtesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.adapter.ImageAdapter
import com.imsosoft.kotlinartbookwithtesting.databinding.FragmentApiSearchBinding
import com.imsosoft.kotlinartbookwithtesting.util.Status
import com.imsosoft.kotlinartbookwithtesting.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ImageApiFragment @Inject constructor(
    val imageAdapter: ImageAdapter
) : Fragment(R.layout.fragment_api_search) {

    private lateinit var binding: FragmentApiSearchBinding
    lateinit var viewModel: ArtViewModel
    private lateinit var job: Job


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        binding = FragmentApiSearchBinding.bind(view)

        job = Job()

        binding.searchText.addTextChangedListener {

            job.cancel()
            job = lifecycleScope.launch {
                delay(1000)
                it?.let {
                    if (it.toString().isNotEmpty()) {
                        viewModel.searchForImage(it.toString())
                    }
                }
            }

        }

        binding.searchText.addTextChangedListener()

        subscribeToObservers()

        binding.imageRecyclerView.adapter = imageAdapter
        binding.imageRecyclerView.layoutManager = GridLayoutManager(requireContext(), 3)

        imageAdapter.setOnItemClickListener {
            findNavController().popBackStack()
            viewModel.setSelectedImage(it)
        }

    }

    private fun subscribeToObservers() {

        viewModel.imageList.observe(viewLifecycleOwner, Observer {

            when(it.status) {
                Status.SUCCESS -> {
                    val urls = it.data?.hits?.map { imageResult -> imageResult.previewURL }
                    imageAdapter.images = urls ?: listOf()
                    binding.progressBar.visibility = View.GONE
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_LONG).show()
                    binding.progressBar.visibility = View.GONE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
            }

        })

    }

}