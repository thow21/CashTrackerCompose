package com.example.cashtrackercompose.ui.entryDetail

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.cashtrackercompose.entry.Entry
import com.example.cashtrackercompose.entry.EntryViewModel
import com.example.cashtrackercompose.ui.baseui.ListEntry

@Composable
fun AllEntriesScreen(
    viewModel: EntryViewModel,
    navController: NavHostController
) {


    val state =
        viewModel.currentState

    AllEntriesView(
        data = state.data,
        navigateToDetail = {
            viewModel.dispatch(EntryViewModel.Action.UpdateDetailEntry(it))
        },
        navController = navController,
    )


}

@Composable
fun AllEntriesView(
    data: List<Entry>,
    navigateToDetail: (Entry) -> Unit,
    navController: NavHostController
) {
    LazyColumn {
        items(data) { entry ->
            ListEntry(
                entry = entry,
                navigateToDetail = navigateToDetail,
                navController = navController,
            )
        }
    }
}