import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
}

val localConfig = Properties().apply {
    val configFile = rootProject.file("local.properties")
    if (configFile.exists()) configFile.inputStream().use { load(it) }
}
val comicVineKey = (localConfig.getProperty("COMIC_VINE_API") ?: "")
    .replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "").replace("\r", "")

android {
    namespace = "com.hexinteractive.wunderwelt"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.hexinteractive.wunderwelt"
        minSdk = 33
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "COMIC_VINE_API", "\"$comicVineKey\"")

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:3.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
