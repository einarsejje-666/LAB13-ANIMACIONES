package com.example.animaciones.juego

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

/** Número de carriles por los que se mueven la nave y los meteoros. */
const val CARRILES = 3
const val VIDAS_INICIALES = 3

/** Fases de una partida. */
enum class FaseJuego { INICIO, JUGANDO, FIN }

/** Un meteoro: carril, altura (0 = arriba, 1 = abajo) y escala de su tamaño. */
data class Meteoro(val id: Long, val carril: Int, val y: Float, val escala: Float)

/** Estado del prototipo: lo lee la interfaz y lo actualiza el bucle de juego. */
class EstadoJuego {
    var fase by mutableStateOf(FaseJuego.INICIO)
        private set
    var puntajeFinal by mutableIntStateOf(0)
        private set
    var carril by mutableIntStateOf(1)
        private set
    var puntos by mutableIntStateOf(0)
        private set
    var vidas by mutableIntStateOf(VIDAS_INICIALES)
        private set
    var golpes by mutableIntStateOf(0)
        private set
    var meteoros by mutableStateOf(emptyList<Meteoro>())
        private set

    private var siguienteId = 0L
    private var esperaNuevo = 0f

    /** Fracción del alto que bajan los meteoros por segundo. */
    private val velocidad get() = 0.32f
    /** Segundos entre un meteoro y el siguiente. */
    private val intervalo get() = 0.95f

    fun iniciar() {
        carril = 1
        puntos = 0
        vidas = VIDAS_INICIALES
        meteoros = emptyList()
        esperaNuevo = 0.6f
        fase = FaseJuego.JUGANDO
    }

    fun mover(direccion: Int) {
        if (fase == FaseJuego.JUGANDO) carril = (carril + direccion).coerceIn(0, CARRILES - 1)
    }

    /** Avanza la partida [dt] segundos. [zonaNave] es la franja vertical que ocupa la nave. */
    fun avanzar(dt: Float, zonaNave: ClosedFloatingPointRange<Float>) {
        if (fase != FaseJuego.JUGANDO) return
        val lista = ArrayList<Meteoro>(meteoros.size + 1)
        esperaNuevo -= dt
        if (esperaNuevo <= 0f) {
            lista += Meteoro(siguienteId++, Random.nextInt(CARRILES), -0.08f, 0.7f + Random.nextFloat() * 0.5f)
            esperaNuevo = intervalo
        }
        var choque = false
        for (m in meteoros) {
            val y = m.y + velocidad * dt
            when {
                m.carril == carril && y in zonaNave -> choque = true
                y > 1.1f -> puntos++
                else -> lista += m.copy(y = y)
            }
        }
        meteoros = lista
        if (choque) {
            vidas--
            golpes++
            if (vidas <= 0) terminar()
        }
    }

    private fun terminar() {
        puntajeFinal = puntos
        meteoros = emptyList()
        fase = FaseJuego.FIN
    }
}
