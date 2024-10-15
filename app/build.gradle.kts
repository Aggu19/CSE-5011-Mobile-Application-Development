plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.dognutritionapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.dognutritionapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation ("androidx.appcompat:appcompat:1.7.0")
    implementation ("com.google.android.material:material:1.12.0")
    implementation ("org.jetbrains.kotlin:kotlin-stdlib:1.9.22")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


}


