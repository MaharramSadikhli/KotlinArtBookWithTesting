package com.imsosoft.kotlinartbookwithtesting.repo

import androidx.lifecycle.LiveData
import com.imsosoft.kotlinartbookwithtesting.model.PixAbayResponse
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtEntity
import com.imsosoft.kotlinartbookwithtesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertArt(art: ArtEntity)

    suspend fun deleteArt(art: ArtEntity)

    fun getArt() : LiveData<List<ArtEntity>>

    suspend fun searchImage(imageString: String): Resource<PixAbayResponse>

}