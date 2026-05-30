plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.tasmorf.plantcalendar.core.navigation"
    compileSdk = 35

    defaultConfig {
        minSdk = 28
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.androidx.navigation.compose)
}
