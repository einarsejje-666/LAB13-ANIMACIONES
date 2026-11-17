package com.example.animaciones.ejercicios

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/** Los tres estados que puede mostrar el ranking. */
enum class EstadoRanking { CARGANDO, CONTENIDO, ERROR }

private val ranking = listOf(
    "Valeria" to 12450,
    "Diego" to 11980,
    "Camila" to 10320,
    "Mateo" to 9875
)

@Composable
fun EjercicioEstados() {
    var estado by rememberSaveable { mutableStateOf(EstadoRanking.CARGANDO) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        EncabezadoEjercicio(
            titulo = "Ranking semanal",
            api = "AnimatedContent",
            indicacion = "Elige un estado para ver cómo cambia el contenido del panel."
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            EstadoRanking.entries.forEach { opcion ->
                val texto = when (opcion) {
                    EstadoRanking.CARGANDO -> "Cargando"
                    EstadoRanking.CONTENIDO -> "Contenido"
                    EstadoRanking.ERROR -> "Error"
                }
                if (opcion == estado) {
                    Button(onClick = {}, modifier = Modifier.weight(1f)) { Text(texto, maxLines = 1) }
                } else {
                    OutlinedButton(onClick = { estado = opcion }, modifier = Modifier.weight(1f)) { Text(texto, maxLines = 1) }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Transición entre estados con la animación por defecto de AnimatedContent
            AnimatedContent(targetState = estado, label = "estadoRanking") { actual ->
                PanelRanking(estado = actual, onReintentar = { estado = EstadoRanking.CARGANDO })
            }
        }
    }
}

@Composable
private fun PanelRanking(estado: EstadoRanking, onReintentar: () -> Unit) {
    when (estado) {
        EstadoRanking.CARGANDO -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
            Text("Cargando ranking…", modifier = Modifier.padding(top = 12.dp))
        }
        EstadoRanking.CONTENIDO -> Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text("Mejores puntajes", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            ranking.forEachIndexed { i, (nombre, puntos) ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text("${i + 1}. $nombre", modifier = Modifier.weight(1f))
                    Text("$puntos pts", fontWeight = FontWeight.Bold)
                }
            }
        }
        EstadoRanking.ERROR -> Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "No se pudo cargar el ranking",
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Bold
            )
            Text("Revisa tu conexión e inténtalo otra vez.", style = MaterialTheme.typography.bodySmall)
            Button(
                onClick = onReintentar,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(top = 12.dp)
            ) { Text("Reintentar") }
        }
    }
}
