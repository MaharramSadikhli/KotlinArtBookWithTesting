package com.imsosoft.kotlinartbookwithtesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.imsosoft.kotlinartbookwithtesting.model.PixAbayResponse
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtEntity
import com.imsosoft.kotlinartbookwithtesting.util.Resource

class FakeArtRepository: ArtRepositoryInterface {

    private val arts = mutableListOf<ArtEntity>()
    private val liveDataArts = MutableLiveData<List<ArtEntity>>()

    override suspend fun insertArt(art: ArtEntity) {
        arts.add(art)
        addLiveDataArt()
    }

    override suspend fun deleteArt(art: ArtEntity) {
        arts.remove(art)
        addLiveDataArt()
    }

    override fun getArt(): LiveData<List<ArtEntity>> {
        return liveDataArts
    }

    override suspend fun searchImage(imageString: String): Resource<PixAbayResponse> {
        return Resource.success(PixAbayResponse(listOf(), 0, 0))
    }

    private fun addLiveDataArt() {
        liveDataArts.postValue(arts)
    }

}