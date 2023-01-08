package com.vulture.superradio.data.repository.station

import com.vulture.superradio.ui.models.Station
import com.vulture.superradio.utils.ResponseState
import kotlinx.coroutines.flow.Flow

interface StationRepository {
    fun getStations() : Flow<ResponseState<List<Station>>>
  //  fun markAsFavourite()
  //  fun removeFromFavourite()

}