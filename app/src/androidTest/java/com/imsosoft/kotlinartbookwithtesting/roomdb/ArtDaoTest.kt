package com.imsosoft.kotlinartbookwithtesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@SmallTest
@ExperimentalCoroutinesApi
class ArtDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var artDb: ArtDB
    private lateinit var artDao: ArtDao

    @Before
    fun setup() {

        artDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDB::class.java
        ).allowMainThreadQueries().build()

        artDao = artDb.artDao()

    }

    @After
    fun teardown() {
        artDb.close()
    }


    @Test
    fun `insert list of arts`() = runTest {

        val art = ArtEntity("John John", "Da Vinci", 1700, "test.com", 1)
        artDao.insertArt(art)

        val listLiveData = artDao.observeArts()
//        val list = listLiveData

    }

}