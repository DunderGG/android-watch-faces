plugins {
    id("com.android.application")
}

android {
    namespace = "gg.dunder.watchfaces.starterdigital"
    compileSdk = 36

    defaultConfig {
        applicationId = "gg.dunder.watchfaces.starterdigital"
        // WFF v4 requires Wear OS 6 (API 36).
        minSdk = 36
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }
}
