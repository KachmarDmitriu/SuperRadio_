package com.vulture.superradio.data.repository.station

import com.vulture.superradio.data.api.StationsApi
import com.vulture.superradio.data.mapper.mapToStation
import com.vulture.superradio.ui.models.Station
import com.vulture.superradio.utils.DataError
import com.vulture.superradio.utils.Loading
import com.vulture.superradio.utils.ResponseState
import com.vulture.superradio.utils.Success
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class StationRepositoryImpl @Inject constructor(
    private val api: StationsApi
) : StationRepository {

    private val favouriteStations : List<String> = listOf<String>()

    override fun getStations(): Flow<ResponseState<List<Station>>> = flow {
        emit(Loading())
        val response = api.getStations()

        val body = response.body()
        if (response.isSuccessful && body != null) {

            val stationsFiltered = body.map { stationPojo ->
                stationPojo.mapToStation()
            }.filter {
                it.imageUrl.isNotEmpty() && it.name.isNotEmpty() && it.audioSourceUrl.isNotEmpty()
            }

                //TODO потрібно пройтись по всіх станціях і змінити isFavourite на true якщо uuid станції є в нашому списку лайкнутих

            emit(Success(stationsFiltered))

        } else {
            emit(DataError(Exception(response.errorBody()?.string())))
        }
    }


    private fun markAsFavourite(favouriteStations: List<String>, station: Station)
     {
         favouriteStations.add() =  station
        // TODO добавление в избранное
    }


    fun removeFromFavourite(favouriteStations: List<String>, station: Station)
    {
        favouriteStations.removeAt() = station
        //TODO удаление из избранного
    }


}