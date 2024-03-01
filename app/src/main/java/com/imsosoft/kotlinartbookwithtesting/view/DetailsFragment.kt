package com.imsosoft.kotlinartbookwithtesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.RequestManager
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.databinding.FragmentDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_details) {

    private lateinit var binding: FragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailsBinding.bind(view)

        binding.saveButton.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToImageApiFragment()
            Navigation.findNavController(it).navigate(action)
        }

    }


}