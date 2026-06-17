package com.example.rickandmortyapp.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.data.location.LocationDetails
import com.example.rickandmortyapp.presentation.location.LocationViewModel
import kotlinx.serialization.Serializable

@Composable
fun LocationScreen(
    context: Context,
    navController: NavController,
    locationData: LocationData,
    paddingValues: PaddingValues
) {

    val locationViewModel: LocationViewModel = viewModel()

    LaunchedEffect(Unit) {
        Log.i("AppLogs", "url: ${locationData.locationUrl}")
        locationViewModel.getLocationDetails(locationData.locationUrl.filter { it.isDigit() }
            .toInt())
    }
    val uiState = locationViewModel.uiState.collectAsState().value

    when (uiState) {
        is LocationViewModel.UIState.IsLoading -> LoadingSpinner(uiState.status)
        is LocationViewModel.UIState.OnSuccess -> LocationDetailsUI(
            navController,
            locationData,
            uiState.locationDetails,
            paddingValues
        )

        is LocationViewModel.UIState.OnError -> Error(context, uiState.error)
        else -> {
            Log.i("AppLogs", " state: ${uiState}")
        }
    }


}

@Composable
fun LocationDetailsUI(
    navController: NavController,
    locationData: LocationData,
    locationDetails: LocationDetails,
    paddingValues: PaddingValues
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .clickable {
                navController.popBackStack()
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AsyncImage(
            modifier = Modifier.size(300.dp),
            model = locationData.imageUrl,
            contentDescription = "image"
        )

        Column {

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Name: ${locationDetails.name}",
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Type: ${locationDetails.type}",
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Dimension: ${locationDetails.dimension}",
                fontSize = 16.sp
            )

            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "Number of residents: ${locationDetails.residents.size}",
                fontSize = 16.sp
            )
        }
    }
}


@Serializable
data class LocationData(val imageUrl: String, val locationUrl: String)