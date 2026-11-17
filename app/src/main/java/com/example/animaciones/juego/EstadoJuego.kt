package com.example.animaciones.juego

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/** Fases de una partida. */
enum class FaseJuego { INICIO, JUGANDO, FIN }

/** Estado del prototipo: lo lee la interfaz y lo actualiza el bucle de juego. */
class EstadoJuego {
    var fase by mutableStateOf(FaseJuego.INICIO)
        private set
    var puntajeFinal by mutableIntStateOf(0)
        private set

    fun iniciar() {
        fase = FaseJuego.JUGANDO
    }
}
