package com.example.animaciones.juego

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/** Color de fondo del área de juego. */
val FondoEspacio = Color(0xFF0B1026)

@Composable
fun PantallaJuego() {
    val estado = remember { EstadoJuego() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Área de juego
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(FondoEspacio)
        ) {
            FondoEstrellas(Modifier.fillMaxSize())
            MenusJuego(estado = estado, modifier = Modifier.fillMaxSize())
        }
    }
}

/** Menús de inicio y de fin: pasan de uno a otro con AnimatedContent. */
@Composable
private fun MenusJuego(estado: EstadoJuego, modifier: Modifier = Modifier) {
    AnimatedContent(
        targetState = estado.fase,
        transitionSpec = {
            (fadeIn(tween(500)) + scaleIn(tween(500), initialScale = 0.85f)) togetherWith
                (fadeOut(tween(300)) + scaleOut(tween(300), targetScale = 1.1f))
        },
        modifier = modifier,
        label = "faseJuego"
    ) { fase ->
        when (fase) {
            FaseJuego.INICIO -> TarjetaMenu(
                titulo = "Lluvia de Meteoros",
                detalle = "Esquiva los meteoros cambiando de carril. Cada meteoro que pasa de largo suma un punto.",
                boton = "Jugar",
                onClick = estado::iniciar
            )
            FaseJuego.FIN -> TarjetaMenu(
                titulo = "Fin de la partida",
                detalle = "Puntaje: ${estado.puntajeFinal}",
                boton = "Jugar otra vez",
                onClick = estado::iniciar
            )
            FaseJuego.JUGANDO -> Box(Modifier.fillMaxSize())
        }
    }
}

@Composable
private fun TarjetaMenu(titulo: String, detalle: String, boton: String, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.55f))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            titulo,
            color = Color.White,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(12.dp))
        Text(detalle, color = Color.White.copy(alpha = 0.85f), textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))
        Button(onClick = onClick) {
            Icon(Icons.Filled.PlayArrow, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(boton)
        }
    }
}
