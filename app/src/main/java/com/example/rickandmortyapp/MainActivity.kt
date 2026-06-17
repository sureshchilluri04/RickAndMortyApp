package com.example.rickandmortyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.rickandmortyapp.screen.GetCharacters
import com.example.rickandmortyapp.screen.LocationData
import com.example.rickandmortyapp.screen.LocationScreen
import com.example.rickandmortyapp.ui.theme.RickAndMortyAppTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RickAndMortyAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                titleContentColor = MaterialTheme.colorScheme.primary,
                            ),
                            title = {
                                Text(
                                    "Rick and Morty",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    },
                ) { innerPadding ->

                    NavigationGraph(innerPadding)
                }
            }
        }
    }

    @Composable
    fun NavigationGraph(paddingValues: PaddingValues) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = "Home") {
            // Home
            composable("Home") {
                // pass the navController
                GetCharacters(this@MainActivity, navController = navController, paddingValues)
            }
            // Profile
            composable<LocationData> { routeValues ->
                val locationData = routeValues.toRoute<LocationData>()
                LocationScreen(this@MainActivity, navController, locationData, paddingValues)
            }

        }
    }
}