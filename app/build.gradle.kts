plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("androidx.navigation.safeargs.kotlin")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.example.marvelscharacters"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.marvelscharacters"
        minSdk = 28
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
        kotlinOptions {
            jvmTarget = "1.8"
        }

        buildFeatures {
            viewBinding = true
            buildConfig = true
        }

        hilt {
            enableAggregatingTask = true
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.appcompat)
        implementation(libs.material)
        implementation(libs.androidx.activity)
        implementation(libs.androidx.constraintlayout)
        implementation(libs.core.splashscreen)
        implementation(libs.okhttp)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        implementation(libs.androidx.navigation.fragment.ktx)
        implementation(libs.androidx.navigation.ui.ktx)
        implementation(libs.androidx.gridlayout)


        // recyclerview.
        implementation(libs.androidx.recyclerview)
        implementation(libs.androidx.cardview)

        // Retrofit.
        implementation(libs.retrofit)
        implementation(libs.retrofit.converter.gson)
        implementation(libs.retrofit.adapter.rxjava3)
        implementation(libs.okhttp.logging)

        // Gson
        implementation(libs.google.gson)

        // Commons Codec
        implementation(libs.commons.codec)

        // Dagger Hilt
        //implementation(libs.hilt.android)
        implementation (libs.hilt.android)
        kapt(libs.hilt.compiler)
        // For instrumentation tests
        androidTestImplementation  (libs.hilt.android.testing)
        kaptAndroidTest (libs.hilt.compiler)
        // For local unit tests
        testImplementation (libs.hilt.android.testing)
        kaptTest (libs.hilt.compiler)

        //Glide
        implementation(libs.glide)
        kapt(libs.glide.kapt)
    }

    kapt {
        correctErrorTypes = true
    }
}

