package com.imsosoft.kotlinartbookwithtesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.imsosoft.kotlinartbookwithtesting.api.PixAbayAPI
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtDB
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
        return Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PixAbayAPI::class.java)
    }

}