// Módulo del codelab "Animating elements in Jetpack Compose" (código inicial de Google).
// Usa las mismas versiones de Gradle, AGP, Kotlin y Compose BOM que el módulo app.
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.android.codelab.animation"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.android.codelab.animation"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation("androidx.compose.material:material-icons-core:1.7.8")
    debugImplementation(libs.androidx.compose.ui.tooling)
}
