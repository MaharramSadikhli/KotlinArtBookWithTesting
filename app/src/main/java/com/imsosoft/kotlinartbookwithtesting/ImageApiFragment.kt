package com.imsosoft.kotlinartbookwithtesting

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.imsosoft.kotlinartbookwithtesting.databinding.FragmentApiSearchBinding

class ImageApiFragment: Fragment(R.layout.fragment_api_search) {

    private lateinit var binding: FragmentApiSearchBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentApiSearchBinding.bind(view)

    }

}