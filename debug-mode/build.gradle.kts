plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.flexnet"
    compileSdk = 34

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    publishing {
        singleVariant("release") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    dependencies {
        // dagger
        implementation(libs.dagger)
        ksp(libs.dagger.compiler)

        // retrofit
        implementation(libs.retrofit)
        implementation(libs.converter.gson)
        implementation(libs.okhttp)
        implementation(libs.logging.interceptor)

        // compose
        implementation(libs.androidx.activity.compose.v161)
        implementation(libs.ui)
        implementation(libs.androidx.lifecycle.viewmodel.compose)
        implementation(libs.androidx.navigation.compose)
        implementation(libs.ui.tooling.preview)
        implementation(libs.ui.tooling)
        implementation(libs.androidx.material)
        implementation(libs.androidx.foundation)

        // room
        ksp(libs.androidx.room.compiler)
        implementation(libs.androidx.room.runtime)
        implementation(libs.androidx.room.ktx)
    }
}

apply(from = rootProject.file("gradle/publish-package.gradle.kts"))



