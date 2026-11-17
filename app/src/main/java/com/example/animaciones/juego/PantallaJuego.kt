package com.example.animaciones.juego

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(FondoEspacio)
                .pointerInput(Unit) {
                    // Tocar la mitad izquierda o derecha también mueve la nave
                    detectTapGestures { toque -> estado.mover(if (toque.x < size.width / 2) -1 else 1) }
                }
        ) {
            // La nave va al centro de su carril con un resorte que rebota un poco
            val anchoCarril = maxWidth / CARRILES
            val xNave by animateDpAsState(
                targetValue = anchoCarril * (estado.carril + 0.5f) - TAMANO_NAVE / 2,
                animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium),
                label = "xNave"
            )
            // Franja vertical (en fracciones del alto) donde un meteoro choca con la nave
            val zonaNave = ((maxHeight - MARGEN_NAVE - TAMANO_NAVE - 12.dp) / maxHeight)..((maxHeight - MARGEN_NAVE) / maxHeight)
            val zonaActual by rememberUpdatedState(zonaNave)

            // Bucle de juego: un paso por fotograma mientras dure la partida
            LaunchedEffect(estado.fase) {
                if (estado.fase != FaseJuego.JUGANDO) return@LaunchedEffect
                var anterior = withFrameNanos { it }
                while (estado.fase == FaseJuego.JUGANDO) {
                    withFrameNanos { ahora ->
                        val dt = ((ahora - anterior) / 1_000_000_000f).coerceAtMost(0.05f)
                        anterior = ahora
                        estado.avanzar(dt, zonaActual)
                    }
                }
            }

            FondoEstrellas(Modifier.fillMaxSize())
            CapaMeteoros(meteoros = { estado.meteoros }, modifier = Modifier.fillMaxSize())
            Nave(
                Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = xNave, y = -MARGEN_NAVE)
            )
            MenusJuego(estado = estado, modifier = Modifier.fillMaxSize())
        }

        // Controles
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
            FilledTonalButton(
                onClick = { estado.mover(-1) },
                enabled = estado.fase == FaseJuego.JUGANDO,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Mover a la izquierda")
            }
            FilledTonalButton(
                onClick = { estado.mover(1) },
                enabled = estado.fase == FaseJuego.JUGANDO,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp)
            ) {
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Mover a la derecha")
            }
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
