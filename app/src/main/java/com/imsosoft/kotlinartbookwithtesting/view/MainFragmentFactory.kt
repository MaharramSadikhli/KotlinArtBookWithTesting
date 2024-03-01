package com.imsosoft.kotlinartbookwithtesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
    private val glide: RequestManager
) : FragmentFactory()
{

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {

        return when (className) {
            DetailsFragment::class.java.name -> DetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }


    }

}