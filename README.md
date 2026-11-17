# Arcade Animado

Aplicación Android en Kotlin y Jetpack Compose con ejemplos de animaciones y un prototipo de videojuego.

## Módulos

| Módulo | Contenido |
|---|---|
| `app` | Pantalla principal con cinco secciones: Visibilidad (`AnimatedVisibility`), Color (`animateColorAsState`), Tamaño y posición (`animateDpAsState` + `Modifier.offset`), Estados (`AnimatedContent`) y Juego. |
| `codelab` | Codelab oficial *Animating elements in Jetpack Compose* resuelto (TODO 1 a 6). |

## Juego: Lluvia de Meteoros

La nave cambia de carril para esquivar meteoros. Combina:

- `rememberInfiniteTransition` para el fondo de estrellas, la llama del propulsor y el giro de los meteoros.
- `animateDpAsState` con resorte para el cambio de carril.
- `AnimatedContent` para el marcador y para pasar entre inicio, partida y fin.
- `AnimatedVisibility` para las vidas y el aviso de nivel.
- `animateColorAsState` para el destello rojo al chocar.

## Cómo ejecutar

1. Abrir la carpeta en Android Studio y sincronizar Gradle (Gradle 9.5.0, AGP 9.3.2, Kotlin 2.2.10).
2. Elegir la configuración `app` o `codelab` y ejecutar en un emulador o dispositivo con Android 7.0 (API 24) o superior.
