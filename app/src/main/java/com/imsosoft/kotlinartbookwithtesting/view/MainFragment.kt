package com.imsosoft.kotlinartbookwithtesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.adapter.ArtAdapter
import com.imsosoft.kotlinartbookwithtesting.databinding.FragmentMainBinding
import com.imsosoft.kotlinartbookwithtesting.viewmodel.ArtViewModel
import javax.inject.Inject

class MainFragment @Inject constructor(
    private val artAdapter: ArtAdapter
): Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[ArtViewModel::class.java]

        binding = FragmentMainBinding.bind(view)

        binding.recyclerViewMain.adapter = artAdapter
        binding.recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())


        binding.fab.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment()
            Navigation.findNavController(it).navigate(action)
        }

        subscribeToObservers()

    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artAdapter.arts = it
        })
    }


}