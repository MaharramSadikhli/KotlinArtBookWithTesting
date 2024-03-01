package com.imsosoft.kotlinartbookwithtesting.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtEntity::class], version = 1)
abstract class ArtDB: RoomDatabase() {
    abstract fun artDao(): ArtDao
}