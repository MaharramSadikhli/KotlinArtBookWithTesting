package com.imsosoft.kotlinartbookwithtesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.imsosoft.kotlinartbookwithtesting.adapter.ArtAdapter
import com.imsosoft.kotlinartbookwithtesting.adapter.ImageAdapter
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
    private val glide: RequestManager,
    private val imageAdapter: ImageAdapter,
    private val artAdapter: ArtAdapter
) : FragmentFactory()
{

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            MainFragment::class.java.name -> MainFragment(artAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageAdapter)
            DetailsFragment::class.java.name -> DetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }


    }

}