package com.imsosoft.kotlinartbookwithtesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class DetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: MainFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun testForNavigationFromDetailsToImageAPI() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailsFragment>( factory = fragmentFactory ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.art_image_view)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            DetailsFragmentDirections.actionDetailsFragmentToImageApiFragment()
        )

    }

    @Test
    fun testForOnBackPressed() {

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailsFragment>( factory = fragmentFactory ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()

    }


    @Test
    fun testForSaveButton() {



    }

}