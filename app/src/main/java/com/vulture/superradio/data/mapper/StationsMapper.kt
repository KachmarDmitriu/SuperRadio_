package com.vulture.superradio.data.mapper

import com.vulture.superradio.data.api.models.StationPojo
import com.vulture.superradio.ui.models.Station

fun StationPojo.mapToStation() : Station = Station(
    uuid = uuid,
    name = name,
    imageUrl = imageUrl,
    genre = tags,
    audioSourceUrl = streamUrl,
    isFavourite = false
)