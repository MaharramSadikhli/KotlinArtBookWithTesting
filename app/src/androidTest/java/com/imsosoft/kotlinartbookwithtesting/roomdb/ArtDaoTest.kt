package com.imsosoft.kotlinartbookwithtesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.imsosoft.kotlinartbookwithtesting.getOrAwaitValueForAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named


@SmallTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("testDb")
    lateinit var artDb: ArtDB

    private lateinit var artDao: ArtDao

    @Before
    fun setup() {

        hiltRule.inject()

        artDao = artDb.artDao()

    }

    @After
    fun teardown() {
        artDb.close()
    }


    @Test
    fun testForInsertArt() = runTest {

        val art = ArtEntity("John John", "Da Vinci", 1700, "test.com", 1)
        artDao.insertArt(art)

        val listLiveData = artDao.observeArts()
        val list = listLiveData.getOrAwaitValueForAndroidTest()

        Truth.assertThat(list).contains(art)

    }


    @Test
    fun testForDeleteArt() = runTest {

        val art = ArtEntity("John John", "Da Vinci", 1700, "test.com", 1)
        artDao.insertArt(art)
        artDao.deleteArt(art)

        val listLiveData = artDao.observeArts()
        val list = listLiveData.getOrAwaitValueForAndroidTest()

        Truth.assertThat(list).doesNotContain(art)

    }

}