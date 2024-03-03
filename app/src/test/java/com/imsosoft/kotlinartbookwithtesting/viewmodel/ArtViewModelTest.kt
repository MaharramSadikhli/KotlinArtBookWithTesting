package com.imsosoft.kotlinartbookwithtesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.imsosoft.kotlinartbookwithtesting.getOrAwaitValueForTest
import com.imsosoft.kotlinartbookwithtesting.repo.FakeArtRepository
import com.imsosoft.kotlinartbookwithtesting.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup() {
        viewModel = ArtViewModel(FakeArtRepository())
    }


    @Test
    fun `insert art without name and returns error`() {
        viewModel.makeArt("", "Name Test", "2024")

        val valueLiveData = viewModel.insertMessage
        val value = valueLiveData.getOrAwaitValueForTest()

        Truth.assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without artist name and returns error`() {
        viewModel.makeArt("Name Test", "", "2024")

        val valueLiveData = viewModel.insertMessage
        val value = valueLiveData.getOrAwaitValueForTest()

        println("Status: ${value.status}")

        Truth.assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without year and returns error`() {
        viewModel.makeArt("Name Test", "test", "")

        val valueLiveData = viewModel.insertMessage
        val value = valueLiveData.getOrAwaitValueForTest()

        println("Status: ${value.status}")

        Truth.assertThat(value.status).isEqualTo(Status.ERROR)

    }

}