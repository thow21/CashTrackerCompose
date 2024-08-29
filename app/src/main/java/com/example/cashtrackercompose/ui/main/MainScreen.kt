package com.example.cashtrackercompose.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cashtrackercompose.Screen
import com.example.cashtrackercompose.entry.EntryViewModel

@Composable
fun MainScreen(
    viewModel: EntryViewModel,
    navController: NavHostController
) {


    MainView(
        navigateToAdd = {
            navController.navigate(Screen.AddScreen.route)
        },
        navigateToAll = {
            navController.navigate(Screen.DetailScreen.route)
            Log.i("Katze", "Navigate to add")
        },
        getData = {
            viewModel.dispatch(EntryViewModel.Action.RefreshData)
        }
    )

}

@Composable
fun MainView(
    navigateToAdd: () -> Unit,
    navigateToAll: () -> Unit,
    getData:() -> Unit
    ) {
    Spacer(modifier = Modifier.size(50.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                navigateToAdd.invoke()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier
                .size(200.dp),

            ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add entry"
            )
        }
        Spacer(modifier = Modifier.height(150.dp))
        Button(
            onClick = {
                navigateToAll.invoke()
                getData.invoke()
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
            modifier = Modifier
                .size(200.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search entries"
            )
        }
    }
}