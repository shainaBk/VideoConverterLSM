// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    id("com.chaquo.python") version "15.0.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}
true // Needed to make the Suppress annotation work for the plugins block