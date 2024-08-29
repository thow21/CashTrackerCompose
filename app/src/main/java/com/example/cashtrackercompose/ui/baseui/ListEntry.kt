package com.example.cashtrackercompose.ui.baseui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cashtrackercompose.Screen
import com.example.cashtrackercompose.entry.Entry

@Composable
fun ListEntry(entry: Entry, navigateToDetail: (Entry) -> Unit, navController: NavHostController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 10.dp)
            .clickable {

                navigateToDetail(entry)
                navController.navigate(Screen.EntryDetail.route)

            }
    ) {
        Text(text = entry.place)
        Spacer(modifier = Modifier.fillMaxWidth(0.6f))
        Text(text = entry.date)
    }
}