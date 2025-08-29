package com.example.biat.interfaces

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biat.globals
import kotlinx.coroutines.runBlocking

@Composable
fun AgentFilInterface(navController: NavController){
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
                        navController.navigate("screen_seven")
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
fun AgentInterface(navController: NavController){
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
                        navController.navigate("screen_eight")
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
fun AgentItems(){
    val list = runBlocking { return@runBlocking getResponse("${globals.idf}/${globals.ide}") }.split(",")
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
                    text = "Votre Historique",
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
        Column (
            Modifier.verticalScroll(rememberScrollState())
        ){
            for (e in list){
                val id = e.split(" ")[0]
                val duration = e.split(" ")[1]
                val date = e.split(" ")[2]
                val s = "Id : $id\n" +
                        "Duree : $duration\n" +
                        "Date : $date"
                AgentItem(s)
            }
        }
    }
}

@Composable
fun AgentItem(s:String,modifier:Modifier=Modifier){
    Surface (
        modifier = Modifier.fillMaxWidth().height(140.dp).padding(15.dp),
        shape = RoundedCornerShape(15.dp),
        color = Color.Blue
    ){
        Text(
            text=s,
            color = Color.White,
            modifier = Modifier.padding(15.dp),
        )
    }
}