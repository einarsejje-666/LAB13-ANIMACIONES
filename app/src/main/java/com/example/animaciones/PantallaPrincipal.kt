package com.example.animaciones

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.animaciones.ejercicios.EjercicioColor
import com.example.animaciones.ejercicios.EjercicioEstados
import com.example.animaciones.ejercicios.EjercicioMovimiento
import com.example.animaciones.ejercicios.EjercicioVisibilidad
import com.example.animaciones.juego.PantallaJuego

/** Secciones de la app, una por cada tipo de animación. */
enum class Seccion(val titulo: String) {
    VISIBILIDAD("Visibilidad"),
    COLOR("Color"),
    MOVIMIENTO("Tamaño y posición"),
    ESTADOS("Estados"),
    JUEGO("Juego")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal() {
    var seccion by rememberSaveable { mutableStateOf(Seccion.VISIBILIDAD) }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                CenterAlignedTopAppBar(
                    title = { Text("Arcade Animado", fontWeight = FontWeight.Bold) }
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(bottom = 8.dp)
                ) {
                    items(Seccion.entries) { opcion ->
                        FilterChip(
                            selected = opcion == seccion,
                            onClick = { seccion = opcion },
                            label = { Text(opcion.titulo) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        // Cambio de sección con un fundido corto
        Crossfade(
            targetState = seccion,
            animationSpec = tween(durationMillis = 350),
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            label = "seccion"
        ) { actual ->
            when (actual) {
                Seccion.VISIBILIDAD -> EjercicioVisibilidad()
                Seccion.COLOR -> EjercicioColor()
                Seccion.MOVIMIENTO -> EjercicioMovimiento()
                Seccion.ESTADOS -> EjercicioEstados()
                Seccion.JUEGO -> PantallaJuego()
            }
        }
    }
}
