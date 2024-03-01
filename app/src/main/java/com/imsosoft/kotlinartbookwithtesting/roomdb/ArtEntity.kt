package com.imsosoft.kotlinartbookwithtesting.roomdb

import androidx.room.PrimaryKey

data class ArtEntity(
    var name: String,
    var artistName: String,
    var year: Int,
    var imageUrl: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) {
}