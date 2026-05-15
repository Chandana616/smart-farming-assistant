package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FarmerApp()
        }
    }
}

@Composable
fun FarmerApp() {

    var nitrogen by remember { mutableStateOf("") }
    var moisture by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "🌾 Raitha-Bharosa Hub",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Nitrogen input
        OutlinedTextField(
            value = nitrogen,
            onValueChange = { nitrogen = it },
            label = { Text("Enter Nitrogen Value") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Moisture input
        OutlinedTextField(
            value = moisture,
            onValueChange = { moisture = it },
            label = { Text("Enter Moisture %") }
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Temperature input
        OutlinedTextField(
            value = temperature,
            onValueChange = { temperature = it },
            label = { Text("Enter Temperature °C") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                val n = nitrogen.toIntOrNull() ?: 0
                val m = moisture.toIntOrNull() ?: 0
                val t = temperature.toIntOrNull() ?: 0

                val index = calculateSowingIndex(n, m, t)

                val status = when {
                    index > 70 -> "GO 🌱 SOW NOW"
                    index > 40 -> "WAIT ⏳"
                    else -> "STOP 🚫"
                }

                result = "Sowing Index: $index% | $status"
            }
        ) {
            Text("Check Soil")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = result,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}

/* ---------------- LOGIC ---------------- */

fun calculateSowingIndex(n: Int, m: Int, t: Int): Int {

    var score = 100

    if (n < 30) score -= 20
    if (m > 35) score -= 30
    if (m < 15) score -= 20
    if (t > 35) score -= 25

    return score.coerceIn(0, 100)
}
