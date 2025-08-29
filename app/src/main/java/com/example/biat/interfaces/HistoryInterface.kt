package com.example.biat.interfaces

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.biat.globals
import kotlinx.coroutines.runBlocking

@Composable
fun HistoryInterface(){
    val list = runBlocking { return@runBlocking getResponse("getIdInfo/${globals.id}") }.split("\n")
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
                val idf = e.split("/")[0].toInt()
                val ide = e.split("/")[1].toInt()
                val duration = e.split(" ")[1].toInt()
                val date = e.split(" ")[2]
                val s = "Filiale : ${
                    runBlocking { return@runBlocking getResponse("filiales") }.split(
                        "\n"
                    ).filter { it.split(",")[0].toInt() == idf }
                        .map { s -> s.split(",")[1] }.first()
                }\n" +
                        "Agent : ${
                            runBlocking {
                                return@runBlocking getResponse(
                                    "${idf}"
                                )
                            }.split("\n")
                                .filter { it.split(",")[0].toInt() == ide }
                                .map { s -> s.split(",")[1] }.first()
                        }\n" +
                        "Id : ${globals.id}\n" +
                        "Duree : ${duration}\n" +
                        "Date : ${date}"
                HistoryItem(s)
            }
        }
    }
}

@Composable
fun HistoryItem(s:String,modifier:Modifier=Modifier){
    Surface (
        modifier = Modifier.fillMaxWidth().height(180.dp).padding(15.dp),
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