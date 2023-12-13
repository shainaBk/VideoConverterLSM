plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    id("com.chaquo.python")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.mobiledevices.videoconverter"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.mobiledevices.videoconverter"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        ndk {
            // Specifies the ABI configurations of your native
            // libraries Gradle should build and package with your app.
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64")
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
}

chaquopy {
    defaultConfig {
        version = "3.11"
        pip {
            // A requirement specifier, with or without a version number:
            install("google-api-python-client")
            install("pytube")
            install("google-auth-httplib2")
            install("google-auth-oauthlib")
        }
        pyc {
            src = false
        }

    }
    sourceSets {
        getByName("main") {
            srcDir("src/main/assets/python")
        }
    }
}

dependencies {
    // AndroidX and UI
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Navigation Components
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Lifecycle Components
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)

    // Serialization and Parsing
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.gson)

    // Firebase
    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-firestore")

    // Image loading
    implementation(libs.coil.compose)

    // Security (hashing)
    implementation(libs.bcprov.jdk15on)

    // Testing Libraries
    testImplementation(libs.junit)
    testImplementation(libs.testng)
    // Kotlin Test Libraries
    testImplementation(libs.kotlin.test.common)
    testImplementation(libs.kotlin.test.annotations.common)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.kotlinx.coroutines.test)
    // Mockito for Mocking in Tests
    testImplementation(libs.mockito.core)

    // Android Instrumental Tests
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.kotlin.test)

    // Annotations
    implementation(libs.annotation)
}
