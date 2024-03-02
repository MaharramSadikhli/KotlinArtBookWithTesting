package com.imsosoft.kotlinartbookwithtesting.view

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.databinding.FragmentDetailsBinding
import com.imsosoft.kotlinartbookwithtesting.util.Status
import com.imsosoft.kotlinartbookwithtesting.viewmodel.ArtViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment @Inject constructor(
    val glide: RequestManager
) : Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: ArtViewModel
    private val name = binding.nameText.text.toString()
    private val artistName = binding.artistText.text.toString()
    private val year = binding.yearText.text.toString()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        binding = FragmentDetailsBinding.bind(view)

        binding.artImageView.setOnClickListener(selectImage)

        binding.saveButton.setOnClickListener(save)

        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        subscribeToObservers()

    }

    private val callBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            viewModel.setSelectedImage("")
            findNavController().popBackStack()
        }

    }

    private val save = OnClickListener {
        viewModel.makeArt(name, artistName, year)
    }

    private val selectImage = OnClickListener {
        val action = DetailsFragmentDirections.actionDetailsFragmentToImageApiFragment()
        Navigation.findNavController(it).navigate(action)
    }

    private fun subscribeToObservers() {

        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer { url ->

            println(url)

            glide.load(url).into(binding.artImageView)

        })

        viewModel.insertMessage.observe(viewLifecycleOwner, Observer {

            when (it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                    viewModel.resetInsertMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message ?: "Error", Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {}
            }
        })

    }


}