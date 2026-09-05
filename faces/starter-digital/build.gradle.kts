// =============================================================================
// STARTER DIGITAL — MODULE BUILD CONFIGURATION
// =============================================================================
// Watch Face Format modules use the standard Android Application plugin to
// bundle XML and graphic resources into an installable APK (debug) or AAB (release).
// =============================================================================

plugins {
    id("com.android.application")
}

android {
    // Namespace: Used by Android build tools for generated R classes and resource mapping.
    namespace = "gg.dunder.watchfaces.starterdigital"

    // compileSdk: The Android SDK version used to compile and validate the module.
    compileSdk = 36

    defaultConfig {
        // applicationId: The globally unique identifier on Google Play and Wear OS.
        // Once published to the Play Store, this ID cannot be changed.
        applicationId = "gg.dunder.watchfaces.starterdigital"

        // minSdk: Specifies the minimum Wear OS platform version required.
        // Watch Face Format v4 corresponds to Wear OS 6 (API level 36).
        // (For broader testing on older Wear OS 4/5 devices, minSdk can be 33/34 with WFF v1/v2).
        minSdk = 36
        targetSdk = 36

        versionCode = 1
        versionName = "0.1.0"
    }
}
