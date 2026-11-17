package com.example.animaciones.ejercicios

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val Azul = Color(0xFF1565C0)
private val Verde = Color(0xFF2E7D32)

/** Especificaciones de animación para comparar en pantalla. */
enum class TipoAnimacion(val etiqueta: String, val detalle: String) {
    TWEEN_RAPIDO("tween 300 ms", "Duración fija y corta, el cambio casi no se nota."),
    TWEEN_LENTO("tween 1500 ms", "Duración fija y larga, se ven los tonos intermedios."),
    SPRING("spring", "Sin duración fija: depende de la rigidez del resorte.")
}

private fun especificacion(tipo: TipoAnimacion): AnimationSpec<Color> = when (tipo) {
    TipoAnimacion.TWEEN_RAPIDO -> tween(durationMillis = 300)
    TipoAnimacion.TWEEN_LENTO -> tween(durationMillis = 1500)
    TipoAnimacion.SPRING -> spring(stiffness = Spring.StiffnessVeryLow)
}

@Composable
fun EjercicioColor() {
    var escudoActivo by rememberSaveable { mutableStateOf(false) }
    var tipo by rememberSaveable { mutableStateOf(TipoAnimacion.TWEEN_LENTO) }
    // El color pasa de forma continua entre azul y verde
    val colorFondo by animateColorAsState(
        targetValue = if (escudoActivo) Verde else Azul,
        animationSpec = especificacion(tipo),
        label = "colorEscudo"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoEjercicio(
            titulo = "Escudo del jugador",
            api = "animateColorAsState",
            indicacion = "El cuadro pasa de azul a verde cuando se activa el escudo."
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            TipoAnimacion.entries.forEach { opcion ->
                FilterChip(
                    selected = opcion == tipo,
                    onClick = { tipo = opcion },
                    label = { Text(opcion.etiqueta) }
                )
            }
        }
        Text(tipo.detalle, style = MaterialTheme.typography.bodySmall)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(colorFondo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                if (escudoActivo) "Escudo activo" else "Escudo apagado",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
        Button(onClick = { escudoActivo = !escudoActivo }, modifier = Modifier.fillMaxWidth()) {
            Text(if (escudoActivo) "Apagar escudo" else "Activar escudo")
        }
    }
}
