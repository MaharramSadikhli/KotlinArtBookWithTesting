package com.imsosoft.kotlinartbookwithtesting.model

data class PixAbayResponse(
    val hits: List<PixAbayResult>,
    val total: Int,
    val totalHits: Int
) {
}