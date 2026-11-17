package com.example.animaciones.juego

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/** Número de carriles por los que se mueven la nave y los meteoros. */
const val CARRILES = 3

/** Fases de una partida. */
enum class FaseJuego { INICIO, JUGANDO, FIN }

/** Estado del prototipo: lo lee la interfaz y lo actualiza el bucle de juego. */
class EstadoJuego {
    var fase by mutableStateOf(FaseJuego.INICIO)
        private set
    var puntajeFinal by mutableIntStateOf(0)
        private set
    var carril by mutableIntStateOf(1)
        private set

    fun iniciar() {
        carril = 1
        fase = FaseJuego.JUGANDO
    }

    fun mover(direccion: Int) {
        if (fase == FaseJuego.JUGANDO) carril = (carril + direccion).coerceIn(0, CARRILES - 1)
    }
}
