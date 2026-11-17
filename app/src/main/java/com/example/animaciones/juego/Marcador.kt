package com.example.animaciones.juego

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun Marcador(puntos: Int, vidas: Int, nivel: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text("Puntos ", style = MaterialTheme.typography.labelLarge)
        // Cada punto nuevo entra desde arriba y el anterior sale por abajo
        AnimatedContent(
            targetState = puntos,
            transitionSpec = {
                (slideInVertically { alto -> -alto } + fadeIn()) togetherWith
                    (slideOutVertically { alto -> alto } + fadeOut())
            },
            label = "puntos"
        ) { valor ->
            Text("$valor", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Text("   Nivel $nivel", style = MaterialTheme.typography.labelLarge)
        Spacer(Modifier.weight(1f))
        // Al perder una vida el corazón crece y se desvanece
        repeat(VIDAS_INICIALES) { i ->
            AnimatedVisibility(
                visible = i < vidas,
                enter = scaleIn() + fadeIn(),
                exit = scaleOut(targetScale = 1.8f) + fadeOut()
            ) {
                Icon(
                    Icons.Filled.Favorite,
                    contentDescription = "Vida",
                    tint = Color(0xFFE53935),
                    modifier = Modifier.padding(horizontal = 2.dp)
                )
            }
        }
    }
}
