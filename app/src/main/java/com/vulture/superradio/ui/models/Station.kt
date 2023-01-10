package com.vulture.superradio.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Station(
    val uuid: String,
    val name: String,
    val imageUrl: String,
    val audioSourceUrl: String,
    val genre: String,
    var isFavourite: Boolean = false,
) : Parcelable