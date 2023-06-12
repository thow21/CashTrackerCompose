package com.example.cashtrackercompose

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cashtrackercompose.ui.theme.CashTrackerComposeTheme
import java.util.Calendar

const val DB_NAME = "Database"

class MainActivity : ComponentActivity() {

    val data = mutableListOf<Entry>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CashTrackerComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Navigation()
                }
            }
        }
    }

    @Composable
    fun AddScreen(navController: NavController) {
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
                //val helper = SQLiteOpenHelper(this, DB_NAME, 1, SQLiteDatabase.OpenParams.Builder())
                val database = android.content.ContextWrapper(applicationContext)
                    .openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
                database.execSQL("CREATE TABLE IF NOT EXISTS entry(Place TEXT, Product TEXT, Notes TEXT, Time TEXT, Cost REAL)")
                val date = Calendar.getInstance().time
                val dateString: String = date.toString()
                val dateShort = dateString.subSequence(0, 19).toString().lowercase()

                val insert = ContentValues()
                insert.put("Place", textPlace.text)
                insert.put("Product", textProduct.text)
                insert.put("Notes", textNotes.text)
                insert.put("Time", dateShort)
                insert.put("Cost", textPrice.text.toFloat())

                database.insert("entry", null, insert)

                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()
                database.close()

                navController.navigate(Screen.MainScreen.route)
            }) {
                Text(text = "Save")
            }
        }

    }

    @Composable
    fun Navigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
            composable(route = Screen.MainScreen.route) {
                MainScreen(navController = navController)
            }
            composable(
                route = Screen.DetailScreen.route

            ) {
                DetailScreen(navController = navController)
            }
            composable(route = Screen.AddScreen.route) {
                AddScreen(navController = navController)
            }

        }
    }

    @Composable
    fun MainScreen(navController: NavController) {
        //Text(text = "Hello $name!")
        Spacer(modifier = Modifier.size(50.dp))
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    navController.navigate(Screen.AddScreen.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier
                    .size(200.dp),

                ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_add_24),
                    contentDescription = "Add entry"
                )
            }
            Spacer(modifier = Modifier.height(150.dp))
            Button(
                onClick = {
                    navController.navigate(Screen.DetailScreen.route)
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier
                    .size(200.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = "Search entries"
                )
            }
        }
    }


    @Composable
    fun DetailScreen(navController: NavController) {
        val dataB = openOrCreateDatabase(DB_NAME, MODE_PRIVATE, null)
        //dataB.execSQL("CREATE TABLE IF NOT EXISTS entry(Place TEXT, Product TEXT, Time TEXT, Cost REAL);")

        val resultSet = dataB.rawQuery("SELECT * FROM entry", null)
        resultSet.moveToFirst()
        if (resultSet.count == 0) {
            Log.i("Katze", "vor resultset")
        }

        for (i in 1..resultSet.count) {
            data.add(
                Entry(
                    tag1 = resultSet.getString(0),
                    tag2 = resultSet.getString(1),
                    notes = resultSet.getString(2),
                    date = resultSet.getString(3),
                    price = resultSet.getFloat(4)
                )
            )
            resultSet.moveToNext()
            Log.i("Katze", "resultset")
        }

        resultSet.close()

        dataB.close()
        //Text(text = "Detail")


        LazyColumn {
            items(data) { entry ->
                ListEntry(entry, navController)
            }
        }

    }

    @Composable
    private fun ListEntry(entry: Entry, navController: NavController) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp)
                .clickable {
                    Toast
                        .makeText(this, entry.price.toString(), Toast.LENGTH_SHORT)
                        .show()
                    navController.navigate(Screen.EntryDetail.route)
                }
        ) {
            Text(text = entry.tag1)
            Spacer(modifier = Modifier.fillMaxSize(0.6f))
            Text(text = entry.date)
        }
    }

    @Composable
    private fun EntryDetail(navController: NavController) {
        Text(text = "Entry detail")
    }
}
