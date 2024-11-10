plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.devtools.ksp")
    id("androidx.navigation.safeargs")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.drawingapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.drawingapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures{
        viewBinding = true
        compose = true
    }
}

dependencies {
    val fragment_version = "1.8.5"
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.runtime.testing.android)
    implementation(libs.androidx.room.ktx)

    implementation(libs.androidx.material3.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    debugImplementation("androidx.fragment:fragment-testing-manifest:$fragment_version")
    androidTestImplementation("androidx.fragment:fragment-testing:$fragment_version")

    androidTestImplementation("androidx.navigation:navigation-testing:2.8.3")
    androidTestImplementation ("androidx.test:runner:1.6.2")
    androidTestImplementation ("androidx.test:rules:1.6.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.0")
    implementation("androidx.fragment:fragment-ktx:1.5.6")
    implementation("androidx.core:core-splashscreen:1.0.0")
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.compose.ui:ui:1.7.5")

    ksp("androidx.room:room-compiler:2.6.1")
}