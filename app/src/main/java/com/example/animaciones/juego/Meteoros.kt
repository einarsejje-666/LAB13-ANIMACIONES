package com.example.animaciones.juego

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp

private val RADIO_METEORO = 20.dp

/** Dibuja los meteoros; la lista se lee dentro del Canvas para redibujar sin recomponer. */
@Composable
fun CapaMeteoros(meteoros: () -> List<Meteoro>, modifier: Modifier = Modifier) {
    // Giro continuo de los cráteres para que los meteoros parezcan rodar
    val transicion = rememberInfiniteTransition(label = "giroMeteoros")
    val giro by transicion.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(tween(durationMillis = 2400, easing = LinearEasing)),
        label = "giro"
    )
    Canvas(modifier = modifier) {
        val anchoCarril = size.width / CARRILES
        meteoros().forEach { m ->
            val centro = Offset(anchoCarril * (m.carril + 0.5f), m.y * size.height)
            val r = RADIO_METEORO.toPx() * m.escala
            // Estela de fuego
            drawLine(
                color = Color(0x66FF7043),
                start = Offset(centro.x, centro.y - r * 2.6f),
                end = centro,
                strokeWidth = r * 1.3f,
                cap = StrokeCap.Round
            )
            drawCircle(Color(0xFF8D6E63), radius = r, center = centro)
            rotate(degrees = if (m.id % 2 == 0L) giro else -giro, pivot = centro) {
                drawCircle(Color(0xFF5D4037), radius = r * 0.28f, center = centro + Offset(r * 0.35f, -r * 0.2f))
                drawCircle(Color(0xFF5D4037), radius = r * 0.18f, center = centro + Offset(-r * 0.4f, r * 0.3f))
            }
        }
    }
}
