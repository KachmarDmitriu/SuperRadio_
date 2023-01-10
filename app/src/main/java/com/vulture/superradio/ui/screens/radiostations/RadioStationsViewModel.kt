package com.vulture.superradio.ui.screens.radiostations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vulture.superradio.data.repository.station.StationRepository
import com.vulture.superradio.data.repository.station.StationRepositoryImpl
import com.vulture.superradio.ui.models.Station
import com.vulture.superradio.utils.DataError
import com.vulture.superradio.utils.Loading
import com.vulture.superradio.utils.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RadioStationsViewModel @Inject constructor(
    private val stationRepository: StationRepository
) : ViewModel() {

    private val _state: MutableStateFlow<RadioStationsState> =
        MutableStateFlow(value = RadioStationsLoading)
    val state: StateFlow<RadioStationsState> = _state

    init {
        loadStations()
    }

    fun loadStations() {
        viewModelScope.launch {
            stationRepository
                .getStations()
                .collect { responseState ->
                    when (responseState) {
                        is Loading -> {
                            _state.value = RadioStationsLoading
                        }
                        is Success -> {
                            val stations = responseState.data ?: emptyList()

                            _state.value = RadioStationsLoaded(stations)
                        }
                        is DataError -> {
                            val errorMessage = responseState.exception?.message
                            _state.value = RadioStationsError(errorMessage)
                        }
                    }
                }
        }
    }


  fun Favourite(station: Station)
  { if (station.isFavourite == false)
    {
          viewModelScope.launch {
          stationRepository
              .markAsFavourite(station)
        }
    }
    else
    {

        viewModelScope.launch {
            stationRepository
                .removeFromFavourite(station)
        }
    }
  }


}