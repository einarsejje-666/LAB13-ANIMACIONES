package com.example.animaciones.ejercicios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun EjercicioVisibilidad() {
    var visible by rememberSaveable { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoEjercicio(
            titulo = "Notificación de logro",
            api = "AnimatedVisibility",
            indicacion = "Toca el botón para mostrar u ocultar la notificación."
        )
        Button(onClick = { visible = !visible }, modifier = Modifier.fillMaxWidth()) {
            Text(if (visible) "Ocultar notificación" else "Mostrar notificación")
        }
        // Primera versión: el cuadro aparece y desaparece de golpe
        Box(modifier = Modifier.fillMaxWidth().height(110.dp)) {
            if (visible) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF6A1B9A)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("¡Logro desbloqueado!", color = Color.White, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
