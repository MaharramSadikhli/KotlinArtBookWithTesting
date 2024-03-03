package com.imsosoft.kotlinartbookwithtesting.viewmodel

import com.google.common.truth.Truth
import com.imsosoft.kotlinartbookwithtesting.getOrAwaitValueForTest
import com.imsosoft.kotlinartbookwithtesting.repo.FakeArtRepository
import com.imsosoft.kotlinartbookwithtesting.util.Status
import org.junit.Before
import org.junit.Test

class ArtViewModelTest {

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

        Truth.assertThat(value).isEqualTo(Status.ERROR)

    }

}