package com.example.animaciones.juego

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.random.Random

private class Estrella(val x: Float, val y: Float, val radio: Float)

@Composable
fun FondoEstrellas(modifier: Modifier = Modifier) {
    val estrellas = remember {
        val azar = Random(seed = 7)
        List(60) { Estrella(azar.nextFloat(), azar.nextFloat(), 0.6f + azar.nextFloat() * 1.4f) }
    }
    val transicion = rememberInfiniteTransition(label = "estrellas")
    // Las estrellas bajan sin parar: da la sensación de que la nave avanza
    val avance by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 9000, easing = LinearEasing)),
        label = "avance"
    )
    // Parpadeo suave, alternado entre estrellas pares e impares
    val brillo by transicion.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 1200), repeatMode = RepeatMode.Reverse),
        label = "brillo"
    )
    Canvas(modifier = modifier) {
        estrellas.forEachIndexed { i, e ->
            val alfa = if (i % 2 == 0) brillo else 1.3f - brillo
            drawCircle(
                color = Color.White.copy(alpha = alfa.coerceIn(0f, 1f)),
                radius = e.radio.dp.toPx(),
                center = Offset(e.x * size.width, ((e.y + avance) % 1f) * size.height)
            )
        }
    }
}
