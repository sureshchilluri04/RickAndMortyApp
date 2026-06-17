package com.example.rickandmortyapp.screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.rickandmortyapp.data.Result
import com.example.rickandmortyapp.presentation.characters.CharactersViewModel

@Composable
fun GetCharacters(context: Context, navController: NavController, paddingValues: PaddingValues) {

    val charactersViewModel: CharactersViewModel = viewModel()

    LaunchedEffect(Unit) {
        charactersViewModel.getCharacters()
    }

    val uiState = charactersViewModel.uiState.collectAsState().value

    when (uiState) {
        is CharactersViewModel.UIState.IsLoading -> {
            LoadingSpinner(uiState.status)
        }

        is CharactersViewModel.UIState.OnSuccess -> {
            CharactersScreen(navController, uiState.results, paddingValues)
        }

        is CharactersViewModel.UIState.OnError -> {
            Error(context, uiState.error)
        }

        else -> {
            Log.i("AppLogs", " state $uiState")
        }
    }
}

@Composable
fun CharactersScreen(
    navController: NavController,
    results: List<Result>,
    paddingValues: PaddingValues
) {

    Column(modifier = Modifier.fillMaxWidth()) {

        LazyColumn(
            contentPadding = paddingValues
        ) {
            items(results.size) { index ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .clickable {
                            navController.navigate(
                                LocationData(
                                    results[index].image,
                                    results[index].location.url
                                )
                            )
                        },
                    elevation = CardDefaults.cardElevation(4.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = results[index].name,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = results[index].status,
                        modifier = Modifier.padding(16.dp)
                    )

                    Text(
                        text = results[index].species,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }

}