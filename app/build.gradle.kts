/* -------------------------------------------------------------------------------------------------
 * Changes that were made:
 *
 * plugins {
 *     id("kotlin-parcelize")
 * }
 *
 * android {
 *     buildFeatures {
 *         viewBinding = true
 *  }
 *
 * dependencies {
 *     implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.29")
 *     implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")
 * }
 *
 * Notes: in 'plugins' section, the order matters! Hence I put the id(parcelise) on the bottom.
 * ---------------------------------------------------------------------------------------------- */

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    id("kotlin-parcelize")
}

android {
    namespace = "hoogvlakte.van.hemel"
    compileSdk = 34

    defaultConfig {
        applicationId = "hoogvlakte.van.hemel"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("pl.droidsonroids.gif:android-gif-drawable:1.2.29")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}