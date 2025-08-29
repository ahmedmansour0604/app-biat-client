package com.example.biat.interfaces

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.biat.R
import com.example.biat.ui.theme.BIATTheme

import io.ktor.client.*
import io.ktor.client.call.body
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.runBlocking


@Composable
fun MainInterface(navController: NavController,modifier: Modifier = Modifier){
    BIATTheme {
        val biatLogo = painterResource(R.drawable.biatlogo)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
            ) {
                Row(
                    modifier = Modifier.padding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = biatLogo,
                        contentDescription = stringResource(R.string.biat_logo),
                        modifier = Modifier
                            .padding(15.dp)
                            .size(150.dp)
                    )
                    Row (
                        modifier = modifier.padding(15.dp)
                    ){
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "History",
                            modifier = Modifier.clickable(onClick = {
                                navController.navigate("screen_six")
                            })
                                .padding(end=15.dp)
                                .size(40.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "History",
                            modifier = Modifier.clickable(onClick = {
                                navController.navigate("screen_five")
                            }).size(40.dp)
                        )
                    }
                }

            }
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ){
                Text(
                    text = "Bienvenue!",
                    fontSize = 30.sp,
                    fontWeight= FontWeight.Bold,
                    modifier=Modifier.padding(bottom = 15.dp),
                )
                Text(
                    text="Souhaitez-vous réserver un rendez-vous ?",
                    modifier=Modifier.padding(bottom = 100.dp)
                )
                Button(
                    onClick = {
                        navController.navigate("screen_two")
                        runBlocking {
                            println(getResponse("1/2"))
                        }
                    },
                    modifier=Modifier.size(250.dp,70.dp),
                    colors= ButtonDefaults.buttonColors(MaterialTheme.colorScheme.tertiary)
                ) {
                    Text(
                        text = "Commencer!",
                        fontSize = 25.sp
                    )
                }
            }
        }
    }
}

suspend fun getResponse(s:String,debugging:Boolean=false): String {
    if(debugging)println("Request Sent : $s")
    val client = HttpClient(CIO)
    val response: HttpResponse = client.get("http://192.168.188.1:8456/$s")
    if(debugging)println("Response Given : ${response.body<String>()}")
    client.close()
    return response.body<String>()
}
