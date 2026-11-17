package com.example.animaciones.juego

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp

val TAMANO_NAVE = 56.dp
val MARGEN_NAVE = 16.dp

@Composable
fun Nave(modifier: Modifier = Modifier) {
    // La llama del propulsor cambia de largo sin parar
    val transicion = rememberInfiniteTransition(label = "propulsor")
    val llama by transicion.animateFloat(
        initialValue = 0.55f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 140), repeatMode = RepeatMode.Reverse),
        label = "llama"
    )
    Canvas(modifier = modifier.size(TAMANO_NAVE)) {
        val w = size.width
        val h = size.height
        val propulsor = Path().apply {
            moveTo(w * 0.38f, h * 0.74f)
            lineTo(w * 0.5f, h * (0.74f + 0.26f * llama))
            lineTo(w * 0.62f, h * 0.74f)
            close()
        }
        drawPath(propulsor, Color(0xFFFFA726))
        val cuerpo = Path().apply {
            moveTo(w * 0.5f, 0f)
            lineTo(w * 0.88f, h * 0.8f)
            lineTo(w * 0.5f, h * 0.66f)
            lineTo(w * 0.12f, h * 0.8f)
            close()
        }
        drawPath(cuerpo, Color(0xFF4FC3F7))
        drawCircle(Color(0xFF0D47A1), radius = w * 0.08f, center = Offset(w * 0.5f, h * 0.38f))
    }
}
