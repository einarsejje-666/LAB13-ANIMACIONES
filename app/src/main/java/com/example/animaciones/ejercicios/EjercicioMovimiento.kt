package com.example.animaciones.ejercicios

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
    // El tamaño crece con tween y la posición se mueve con un resorte
    val tamano by animateDpAsState(
        targetValue = if (potenciado) 110.dp else 60.dp,
        animationSpec = tween(durationMillis = 700),
        label = "tamanoNave"
    )
    val desplazamientoX by animateDpAsState(
        targetValue = if (potenciado) 170.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "desplazamientoX"
    )
    val desplazamientoY by animateDpAsState(
        targetValue = if (potenciado) 70.dp else 0.dp,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow),
        label = "desplazamientoY"
    )

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

        Text("Orden de los modificadores", style = MaterialTheme.typography.titleMedium)
        // offset va primero: se mueve la caja completa con su fondo
        PistaComparacion(
            titulo = "offset → background",
            descripcion = "El desplazamiento se aplica antes de pintar, así que el fondo viaja con la estrella.",
            modificadorCaja = Modifier
                .offset(x = desplazamientoX)
                .size(56.dp)
                .background(ColorNave)
        )
        // background va primero: el fondo queda en su sitio y solo se mueve el contenido
        PistaComparacion(
            titulo = "background → offset",
            descripcion = "El fondo se pinta en la posición original y solo la estrella se desplaza.",
            modificadorCaja = Modifier
                .size(56.dp)
                .background(ColorNave)
                .offset(x = desplazamientoX)
        )
    }
}

@Composable
private fun PistaComparacion(titulo: String, descripcion: String, modificadorCaja: Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(titulo, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp)
        ) {
            Box(modifier = modificadorCaja, contentAlignment = Alignment.Center) {
                Icon(Icons.Filled.Star, contentDescription = null, tint = Color.White)
            }
        }
        Text(descripcion, style = MaterialTheme.typography.bodySmall)
    }
}
