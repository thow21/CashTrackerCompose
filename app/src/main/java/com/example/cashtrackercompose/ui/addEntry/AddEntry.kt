package com.example.cashtrackercompose.ui.addEntry

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.cashtrackercompose.Screen
import com.example.cashtrackercompose.entry.Entry
import com.example.cashtrackercompose.entry.EntryViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddScreen(
    navController: NavHostController,
    viewModel: EntryViewModel = koinViewModel()
) {

    AddView(
        saveEntry = {
            viewModel.dispatch(EntryViewModel.Action.AddEntry(it))
            navController.navigate(Screen.MainScreen.route)
            viewModel.dispatch(EntryViewModel.Action.RefreshData)
        }
    )

}


@Composable
fun AddView(
    saveEntry: (Entry) -> Unit,

    ) {

    var textPlace by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textProduct by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textPrice by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var textNotes by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = textPlace, onValueChange = {
            textPlace = it
        },
            placeholder = {
                Text(text = "Place")
            })
        TextField(value = textProduct, onValueChange = {
            textProduct = it
        },
            placeholder = {
                Text(text = "Product")
            })
        TextField(
            value = textPrice,
            placeholder = {
                Text(text = "Price")
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = {
                textPrice = it
            }
        )
        Spacer(
            modifier = Modifier
                .size(50.dp)
        )
        TextField(value = textNotes, onValueChange = {
            textNotes = it
        },
            placeholder = {
                Text(text = "Further Notes")
            })
        Spacer(modifier = Modifier.size(100.dp))
        Button(onClick = {
            saveEntry.invoke(
                Entry(
                    place = textPlace.text,
                    product = textProduct.text,
                    notes = textNotes.text,
                    price = textPrice.text.toFloat(),
                    date = "now"
                )
            )
        }) {
            Text(text = "Save")
        }
    }
}