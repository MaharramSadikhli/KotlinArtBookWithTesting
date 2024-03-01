package com.imsosoft.kotlinartbookwithtesting.api

import com.imsosoft.kotlinartbookwithtesting.model.PixAbayResponse
import retrofit2.http.Query
import com.imsosoft.kotlinartbookwithtesting.util.Utils
import retrofit2.Response
import retrofit2.http.GET

interface PixAbayAPI {

    @GET(Utils.PATH)
    suspend fun imageSearch(
        @Query(Utils.QUERY_KEY) apiKey: String = Utils.API_KEY,
        @Query(Utils.QUERY_SEARCH) searchQuery: String,
    ) : Response<PixAbayResponse>

}