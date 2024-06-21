plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.capstone.signlingo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.capstone.signlingo"
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
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
//    implementation("androidx.appcompat:appcompat:1.3.1")
//    implementation("androidx.viewpager:viewpager:1.0.0")
//    implementation("com.google.android.material:material:1.4.0")

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.viewpager)
    implementation(libs.circleindicator)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.vision.common)
    implementation(libs.pose.detection.common)
//    implementation(libs.mediation.test.suite)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.ucrop)
    implementation (libs.glide)

    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.google.android.material:material:1.3.0")
    implementation("me.relex:circleindicator:2.1.6")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")





}