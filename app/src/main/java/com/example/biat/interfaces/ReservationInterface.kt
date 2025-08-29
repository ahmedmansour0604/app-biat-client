package com.example.biat.interfaces

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biat.globals
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun ReservationInterface(navController: NavController){
    val list = runBlocking { return@runBlocking getResponse("filiales") }
    Column {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selectionner une filiale s'il vous plait",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (fil in list.split("\n")) {
                val f = fil.split(",")
                Button(
                    onClick = {
                        globals.idf=f[0].toInt()
                        navController.navigate("screen_three")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .size(300.dp, 100.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = f[1].capitalize(),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun ChooseAgent(navController: NavController){
    val list = runBlocking { return@runBlocking getResponse("${globals.idf}") }
    Column {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selectionner un agent s'il vous plait",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (fil in list.split("\n")) {
                val f = fil.split(",")
                Button(
                    onClick = {
                        globals.ide=f[0].toInt()
                        navController.navigate("screen_four")
                    },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .size(300.dp, 100.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = f[1].capitalize(),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChooseType(navController: NavController){
    val list =mapOf(
        "Creation de compte" to 15,
        "Creation de carte" to 10,
        "Virement / Retrait" to 5,

    )
    Column {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Selectionner un agent s'il vous plait",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            for (fil in list) {
                val showDateDialog = remember { mutableStateOf(false) }
                val showConfirmDialog = remember { mutableStateOf(false) }
                Button(
                    onClick = {
                        globals.duration = fil.value
                        showDateDialog.value = true
                    },
                    colors = ButtonDefaults.buttonColors(Color.Blue),
                    modifier = Modifier
                        .size(300.dp, 100.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(15.dp)
                ) {
                    Text(
                        text = fil.key.capitalize(),
                        color = MaterialTheme.colorScheme.primary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                val datePickerState = rememberDatePickerState()
                if (showDateDialog.value){
                    DatePickerDialog(
                        onDismissRequest = {showDateDialog.value=false},
                        confirmButton = {
                            TextButton(
                                onClick = {
                                    if (datePickerState.selectedDateMillis!=null) {
                                        showDateDialog.value=false
                                        showConfirmDialog.value=true
                                        globals.date = (SimpleDateFormat("dd-MM-yyyy").format(Date(datePickerState.selectedDateMillis!!)))
                                    }
                                }
                            ) {
                                Text("Confimer Date",color=Color.Black)
                            }


                        }
                    ) {
                        DatePicker(
                            state = datePickerState,
                            modifier = Modifier.verticalScroll(rememberScrollState()),
                        )
                    }
                }

                if (showConfirmDialog.value){
                    AlertDialog(
                        onDismissRequest = { showConfirmDialog.value = false },
                        title = { Text("Confirmation") },
                        text = {
                            Column {
                                Text("Voulez-vous confirmer ce rendez-vous ?")
                                val x = runBlocking { return@runBlocking getResponse("time/${globals.idf}/${globals.ide}/${globals.date}")}.toInt()
                                val time = "${9+x/60}:${x%60}"
                                Text(
                                    text = "Filiale : ${
                                    runBlocking { return@runBlocking getResponse("filiales") }.split(
                                        "\n"
                                    ).filter { it.split(",")[0].toInt() == globals.idf }
                                        .map { s -> s.split(",")[1] }.first()
                                }\n" +
                                        "Agent : ${
                                            runBlocking {
                                                return@runBlocking getResponse(
                                                    "${globals.idf}"
                                                )
                                            }.split("\n")
                                                .filter { it.split(",")[0].toInt() == globals.ide }
                                                .map { s -> s.split(",")[1] }.first()
                                        }\n" +
                                        "Id : ${globals.id}\n" +
                                        "Duree : ${globals.duration}\n" +
                                        "Date : ${globals.date}\n" +
                                            "Temps Prevu : $time",
                                    fontSize = 10.sp
                                )
                            }
                               },
                        confirmButton = {
                            TextButton(onClick = {
                                showConfirmDialog.value = false
                                runBlocking {
                                    getResponse("${globals.idf}/${globals.ide}/${globals.id}/${globals.duration}/${globals.date}")
                                }
                            }) {
                                Text(text = "Oui",color=Color.Black)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showConfirmDialog.value = false }) {
                                Text(text = "Non",color=Color.Black)
                            }
                        }
                    )
                }
            }
        }
    }
}

