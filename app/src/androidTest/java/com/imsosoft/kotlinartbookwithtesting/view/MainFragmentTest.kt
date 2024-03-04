package com.imsosoft.kotlinartbookwithtesting.view

import androidx.navigation.NavController
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
class MainFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Inject
    lateinit var fragmentFactory: MainFragmentFactory


    @Before
    fun setup() {
        hiltRule.inject()
    }


    @Test
    fun testFabButtonForNavigationToDetailFragment() {
        val navController = Mockito.mock(NavController::class.java)


    }

}