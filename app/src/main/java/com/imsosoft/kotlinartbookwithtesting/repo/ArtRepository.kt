package com.imsosoft.kotlinartbookwithtesting.repo

import androidx.lifecycle.LiveData
import com.imsosoft.kotlinartbookwithtesting.api.PixAbayAPI
import com.imsosoft.kotlinartbookwithtesting.model.PixAbayResponse
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtDao
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtEntity
import com.imsosoft.kotlinartbookwithtesting.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val pixAbayApi: PixAbayAPI
): ArtRepositoryInterface
{
    override suspend fun insertArt(art: ArtEntity) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: ArtEntity) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<ArtEntity>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<PixAbayResponse> {
        return try {

            val response = pixAbayApi.imageSearch(imageString)

            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }

        } catch (e: Exception) {
            Resource.error("No Data!", null)
        }
    }
}