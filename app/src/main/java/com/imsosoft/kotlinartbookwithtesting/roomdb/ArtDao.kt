package com.imsosoft.kotlinartbookwithtesting.roomdb

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: ArtEntity)

    @Delete
    suspend fun deleteArt(art: ArtEntity)

    @Query("SELECT * FROM arts")
    fun observeArts() : LiveData<List<ArtEntity>>

}