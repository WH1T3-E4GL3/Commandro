plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.commandro"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.commandro"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"  // Make sure this matches your Compose version
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))

    // Direct references to Jetpack Compose libraries
    implementation("androidx.compose.ui:ui:1.5.1") // Change version as necessary
    implementation("androidx.compose.ui:ui-tooling-preview:1.5.1") // Change version as necessary
    implementation("androidx.compose.material3:material3:1.0.0") // Change version as necessary

    implementation("com.squareup.okhttp3:okhttp:4.9.1")  // Networking library

    // Testing dependencies
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.5.1") // Change version as necessary

    debugImplementation("androidx.compose.ui:ui-tooling:1.5.1") // Change version as necessary
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.5.1") // Change version as necessary
}
