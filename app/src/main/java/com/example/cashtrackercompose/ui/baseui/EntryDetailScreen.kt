package com.example.cashtrackercompose.ui.baseui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.cashtrackercompose.Screen
import com.example.cashtrackercompose.entry.Entry
import com.example.cashtrackercompose.entry.EntryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EntryDetailScreen(
    viewModel: EntryViewModel = koinViewModel(),
    navController: NavHostController
) {

    val state = viewModel.state.collectAsState()

    EntryDetailView(
        entry = state.value.detailEntry,
        deleteEntry = {
            viewModel.dispatch(EntryViewModel.Action.DeleteEntry)
        },
        navController = navController,
    )

}


@Composable
fun EntryDetailView(
    entry: Entry?,
    deleteEntry: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    Column(
        modifier = modifier,
    ) {
        Row {
            Text(text = entry?.place ?: "")
            Text(text = entry?.product ?: "")
        }
        Row() {
            Text(text = entry?.price.toString())
            Text(text = entry?.date ?: "")
        }
        Text(text = entry?.notes ?: "")

        Button(onClick = {
            deleteEntry.invoke()
            navController.navigate(Screen.DetailScreen.route)

        }) {
            Text(text = "Delete")
        }
    }
}