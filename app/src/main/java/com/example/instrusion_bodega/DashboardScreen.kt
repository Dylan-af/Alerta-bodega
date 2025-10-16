package com.example.instrusion_bodega

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.ServerSocket
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class LogEntry(val deviceName: String, val ipAddress: String, val timestamp: String)

@Composable
fun DashboardScreen() {
    var alarmActive by remember { mutableStateOf(false) }
    val logEntries = remember { mutableStateListOf<LogEntry>() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            val serverSocket = ServerSocket(8080)
            while (true) {
                try {
                    val socket = serverSocket.accept()
                    val message = socket.getInputStream().bufferedReader().use { it.readText() }
                    val parts = message.split("|")
                    if (parts.getOrNull(0) == "ALERTA") {
                        val deviceName = parts.getOrNull(1) ?: "Dispositivo Desconocido"
                        val ipAddress = socket.inetAddress.hostAddress ?: "IP Desconocida"
                        val timestamp = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
                        val newLog = LogEntry(deviceName, ipAddress, timestamp)
                        
                        withContext(Dispatchers.Main) {
                            alarmActive = true
                            logEntries.add(0, newLog) // Add to the top of the list
                        }
                    }
                    socket.close()
                } catch (e: Exception) {
                    // Handle exceptions
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (alarmActive) {
            Text(
                text = "¡ALARMA ACTIVADA!",
                color = Color.Red,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { alarmActive = false },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(text = "Restablecer Alarma", color = Color.White)
            }
        } else {
            Text("Esperando alertas...", style = MaterialTheme.typography.headlineSmall)
            Text("(Asegúrate de estar en la misma red Wi-Fi)")
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Log Display
        Text("Registro de Alertas:", style = MaterialTheme.typography.titleMedium)
        LazyColumn(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
            items(logEntries) { log ->
                Text("Dispositivo: ${log.deviceName}, IP: ${log.ipAddress}, Hora: ${log.timestamp}")
            }
        }
    }
}