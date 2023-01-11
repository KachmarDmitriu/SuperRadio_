package com.vulture.superradio.ui.screens.radiostations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.vulture.superradio.ui.models.Station
import com.vulture.superradio.ui.screens.destinations.PlayerScreenDestination
import com.vulture.superradio.ui.screens.elements.FavoriteButton

@Destination(start = true)
@Composable
fun RadioStationsScreen(
    navigator: DestinationsNavigator, viewModel: RadioStationsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    when (val screenState = state) {
        is RadioStationsLoading -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        is RadioStationsLoaded -> {
            RadioStationsList(modifier = Modifier,
                stations = screenState.items,
                onItemClick = { station ->
                    navigator.navigate(
                        PlayerScreenDestination(
                            station = station
                        )
                    )
                },
                onFavoriteClick = {
                        station ->
                    navigator.navigate(
                       PlayerScreenDestination(
                            station = station

                        )

                    )
                    viewModel.Favourite(station)

                //TODO тык по кнопке избранного
                })
        }
        is RadioStationsError -> {
            Text(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                text = screenState.message ?: "Error :(",
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun RadioStationsList(
    modifier: Modifier = Modifier,
    stations: List<Station>,
    onItemClick: (Station) -> Unit,
    onFavoriteClick: (Station) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(stations) { station ->
            RadioStationItem(station = station,
                onItemClick = { onItemClick.invoke(station) },
                onFavoriteClick = { onFavoriteClick.invoke(station) })
        }
    }
}

@Composable
fun RadioStationItem(
    modifier: Modifier = Modifier,
    station: Station,
    onItemClick: () -> Unit,
    onFavoriteClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable(onClick = onItemClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.size(64.dp),
            model = station.imageUrl,
            contentDescription = "Station image",
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 8.dp)
        ) {
            Text(text = station.name, fontWeight = FontWeight.Bold)
            Text(text = station.genre, maxLines = 1)
        }
        FavoriteButton(
            isFavorite = station.isFavourite, onClick = onFavoriteClick
        )
    }
}