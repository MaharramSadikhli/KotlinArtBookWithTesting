package com.imsosoft.kotlinartbookwithtesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.adapter.ImageAdapter
import com.imsosoft.kotlinartbookwithtesting.getOrAwaitValueForAndroidTest
import com.imsosoft.kotlinartbookwithtesting.launchFragmentInHiltContainer
import com.imsosoft.kotlinartbookwithtesting.repo.FakeArtRepository
import com.imsosoft.kotlinartbookwithtesting.viewmodel.ArtViewModel
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
class ImageApiFragmentTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var fragmentFactory: MainFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun testForSelectImage() {

        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "test.com"
        val testViewModel = ArtViewModel(FakeArtRepository())


        launchFragmentInHiltContainer<ImageApiFragment>(
            factory = fragmentFactory
        ) {
            viewModel = testViewModel
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(selectedImageUrl)
        }


        Espresso.onView(ViewMatchers.withId(R.id.image_recycler_view)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(0, ViewActions.click())
        )

        Mockito.verify(navController).popBackStack()

        Truth.assertThat(testViewModel.selectedImageUrl.getOrAwaitValueForAndroidTest()).isEqualTo(selectedImageUrl)

    }


}