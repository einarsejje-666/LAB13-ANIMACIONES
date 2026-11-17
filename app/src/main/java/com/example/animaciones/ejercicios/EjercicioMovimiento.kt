package com.example.animaciones.ejercicios

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp

private val ColorNave = Color(0xFFEF6C00)

@Composable
fun EjercicioMovimiento() {
    var potenciado by rememberSaveable { mutableStateOf(false) }
    // Primera versión: tamaño y posición cambian al instante
    val tamano = if (potenciado) 110.dp else 60.dp
    val desplazamientoX = if (potenciado) 170.dp else 0.dp
    val desplazamientoY = if (potenciado) 70.dp else 0.dp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoEjercicio(
            titulo = "Nave con potenciador",
            api = "animateDpAsState + Modifier.offset",
            indicacion = "Al tomar el potenciador la nave avanza y crece."
        )
        // Pista donde se mueve la nave
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = desplazamientoX, y = desplazamientoY)
                    .size(tamano)
                    .clip(RoundedCornerShape(16.dp))
                    .background(ColorNave),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Filled.Star, contentDescription = "Nave", tint = Color.White)
            }
        }
        Button(onClick = { potenciado = !potenciado }, modifier = Modifier.fillMaxWidth()) {
            Text(if (potenciado) "Volver al inicio" else "Tomar potenciador")
        }
    }
}
