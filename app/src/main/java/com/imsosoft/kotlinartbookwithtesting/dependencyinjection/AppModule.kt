package com.imsosoft.kotlinartbookwithtesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.imsosoft.kotlinartbookwithtesting.R
import com.imsosoft.kotlinartbookwithtesting.api.PixAbayAPI
import com.imsosoft.kotlinartbookwithtesting.repo.ArtRepository
import com.imsosoft.kotlinartbookwithtesting.repo.ArtRepositoryInterface
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtDB
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtDao
import com.imsosoft.kotlinartbookwithtesting.util.Utils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    // db injection
    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, ArtDB::class.java, "ArtBookDB").build()


    // dao injection
    @Singleton
    @Provides
    fun injectDao(db: ArtDB) = db.artDao()


    // api injection
    @Singleton
    @Provides
    fun injectPixAbayApi(): PixAbayAPI {
        return Retrofit.Builder().baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(PixAbayAPI::class.java)
    }


    // repo injection
    @Singleton
    @Provides
    fun injectRepo(
        artDao: ArtDao, api: PixAbayAPI
    ) = ArtRepository(artDao, api) as ArtRepositoryInterface


    // glide injection
    @Singleton
    @Provides
    fun injectGlide(
        @ApplicationContext context: Context
    ) = Glide
        .with(context).setDefaultRequestOptions(
            RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )

}