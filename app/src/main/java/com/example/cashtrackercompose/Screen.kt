package com.example.cashtrackercompose

sealed class Screen(val route : String) {
    data object MainScreen : Screen("main_screen")
    data object DetailScreen : Screen("detail_screen")
    data object AddScreen : Screen("add_screen")
    data object EntryDetail : Screen("entry_detail")
}
