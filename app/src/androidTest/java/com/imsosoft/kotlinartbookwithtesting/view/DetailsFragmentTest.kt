package com.imsosoft.kotlinartbookwithtesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.getOrAwaitValueForAndroidTest
import com.imsosoft.kotlinartbookwithtesting.launchFragmentInHiltContainer
import com.imsosoft.kotlinartbookwithtesting.repo.FakeArtRepository
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtEntity
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

        val testViewModel = ArtViewModel(FakeArtRepository())

        launchFragmentInHiltContainer<DetailsFragment>(
            factory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.name_text)).perform(ViewActions.replaceText("Mono Lisa"))
        Espresso.onView(ViewMatchers.withId(R.id.artist_text)).perform(ViewActions.replaceText("Leonardo da Vinci"))
        Espresso.onView(ViewMatchers.withId(R.id.year_text)).perform(ViewActions.replaceText("1452"))
        Espresso.onView(ViewMatchers.withId(R.id.save_button)).perform(ViewActions.click())

        Truth.assertThat(testViewModel.artList.getOrAwaitValueForAndroidTest()).contains(
            ArtEntity(
                "Mono Lisa",
                "Leonardo da Vinci",
                1452,
                ""
            )
        )

    }

}