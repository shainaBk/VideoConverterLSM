@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
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

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.annotation)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    testImplementation(libs.junit)
    testImplementation("org.testng:testng:6.9.6")
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("com.google.code.gson:gson:2.8.6")
    // Ajoutez les dépendances pour kotlin.test
    testImplementation("org.jetbrains.kotlin:kotlin-test-common:1.8.22")
    testImplementation("org.jetbrains.kotlin:kotlin-test-annotations-common:1.8.22")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.22")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    // Pour les tests Android instrumentalisés
    androidTestImplementation("org.jetbrains.kotlin:kotlin-test:1.8.22")
    testImplementation("org.mockito:mockito-core:3.11.2") // Remplacez 3.x.x par la dernière version

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
    implementation("com.google.firebase:firebase-firestore")



    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    // dependance for hash
    implementation("org.bouncycastle:bcprov-jdk15on:1.70")




}