package com.example.cashtrackercompose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cashtrackercompose.Screen
import com.example.cashtrackercompose.entry.EntryViewModel
import com.example.cashtrackercompose.ui.addEntry.AddScreen
import com.example.cashtrackercompose.ui.baseui.EntryDetailScreen
import com.example.cashtrackercompose.ui.entryDetail.AllEntriesScreen
import com.example.cashtrackercompose.ui.main.MainScreen
import org.koin.androidx.compose.koinViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val viewModel = koinViewModel<EntryViewModel>()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.DetailScreen.route) {
            AllEntriesScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.AddScreen.route) {
            AddScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.EntryDetail.route) {
            EntryDetailScreen(navController = navController, viewModel = viewModel)
        }
    }
}