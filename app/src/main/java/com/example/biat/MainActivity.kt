package com.example.biat

import android.content.Context
import android.content.ContextParams
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.biat.interfaces.AgentFilInterface
import com.example.biat.interfaces.AgentInterface
import com.example.biat.interfaces.AgentItems
import com.example.biat.interfaces.ChooseAgent
import com.example.biat.interfaces.ChooseType
import com.example.biat.interfaces.HistoryInterface
import com.example.biat.ui.theme.BIATTheme
import com.example.biat.interfaces.MainInterface
import com.example.biat.interfaces.ReservationInterface
import com.example.biat.interfaces.getResponse
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val sharedPrefs = this.getSharedPreferences("id",Context.MODE_PRIVATE)
        globals.id = sharedPrefs.getInt("id",-1)
        if (globals.id == -1){
            globals.id = runBlocking { return@runBlocking getResponse("getId").toInt() }
            sharedPrefs.edit().putInt("id", globals.id?:-1).apply()
        }
        setContent {
            BIATTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Navigator()
                }
            }
        }
    }
}

object globals{
    var id :Int?=null
    var idf : Int?=null
    var duration : Int?=null
    var ide : Int?=null
    var date : String?=null
}
@Composable
fun Navigator(modifier: Modifier=Modifier){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "screen_one") {
        composable("temp") { Temp(navController) }

        composable("screen_one") { MainInterface(navController) }
        composable("screen_two") { ReservationInterface(navController) }
        composable("screen_three") { ChooseAgent(navController) }
        composable("screen_four") { ChooseType(navController) }
        composable("screen_five") { HistoryInterface() }
        composable("screen_six") { AgentFilInterface(navController) }
        composable("screen_seven") { AgentInterface(navController) }
        composable("screen_eight") { AgentItems() }



    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BIATTheme {
        Navigator()
    }
}

@Composable
fun Temp(navController: NavController,modifier:Modifier=Modifier){
    Surface(
        modifier=Modifier.fillMaxSize(),
        color= MaterialTheme.colorScheme.tertiary
    ) {
    }
}
