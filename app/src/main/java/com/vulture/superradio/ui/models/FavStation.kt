package com.vulture.superradio.ui.models

data class FavStation(
    val name: String,
    val imageUrl: String,
    val audioSourceUrl: String,
    val genre: String,
    val isFavourite: Boolean
)
