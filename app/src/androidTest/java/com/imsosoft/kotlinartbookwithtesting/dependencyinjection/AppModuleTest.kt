package com.imsosoft.kotlinartbookwithtesting.dependencyinjection

import androidx.room.Room
import com.imsosoft.kotlinartbookwithtesting.roomdb.ArtDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {

    @Provides
    @Named("testDb")
    fun injectInMemoryRoomDatabase(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, ArtDB::class.java)
            .allowMainThreadQueries().build()

}