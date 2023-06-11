package com.example.cashtrackercompose

sealed class Screen(val route : String) {
    object MainScreen : Screen("main_screen")
    object DetailScreen : Screen("detail_screen")
    object AddScreen : Screen("add_screen")
}
