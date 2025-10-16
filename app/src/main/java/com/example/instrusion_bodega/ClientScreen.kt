package com.example.instrusion_bodega

import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.Socket

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientScreen() {
    val coroutineScope = rememberCoroutineScope()
    var serverIp by remember { mutableStateOf("") }
    var status by remember { mutableStateOf("Estado: Inactivo") }
    val deviceName = Build.MODEL

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Actuando como Cliente (Sensor)", style = MaterialTheme.typography.headlineSmall)
        Text("Dispositivo: $deviceName")
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = serverIp,
            onValueChange = { serverIp = it },
            label = { Text("IP del Servidor (tu teléfono)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (serverIp.isNotBlank()) {
                    status = "Enviando alerta..."
                    coroutineScope.launch(Dispatchers.IO) {
                        try {
                            Socket(serverIp, 8080).use { socket ->
                                // Formato: ALERTA|NombreDelDispositivo
                                val message = "ALERTA|$deviceName"
                                socket.getOutputStream().write(message.toByteArray())
                            }
                            status = "Alerta enviada con éxito"
                        } catch (e: Exception) {
                            status = "Error al enviar: ${e.message}"
                        }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
        ) {
            Text(text = "Simular Detección de Movimiento", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(status)
    }
}