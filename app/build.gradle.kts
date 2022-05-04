plugins {
    id(Config.com_android_application)
    id(Config.org_jetbrains_kotlin_android)
    kotlin("kapt")
    id(Config.com_google_dagger_hilt_android)
}

android {
    compileSdk = Config.compile_target_sdk

    defaultConfig {
        applicationId = "com.mayurbhola.timer"

        minSdk = Config.min_sdk
        targetSdk = Config.compile_target_sdk

        versionCode = Config.version_code
        versionName = Config.version_name

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.mayurbhola.timer.HiltTestRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isShrinkResources = true
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures.compose = true

    composeOptions.kotlinCompilerExtensionVersion = Config.compose_version

    packagingOptions.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
}

dependencies {

    implementation(Deps.CoreKTX.core_ktx)

    implementation(Deps.Lifecycle.lifecycle_runtime_ktx)
    implementation(Deps.Lifecycle.lifecycle_viewmodel_compose)
    implementation(Deps.Lifecycle.lifecycle_viewmodel_ktx)

    implementation(Deps.ActivityCompose.activity_compose)

    implementation(Deps.Compose.ui)
    implementation(Deps.Compose.ui_tooling_preview)

    implementation(Deps.Compose.material)
    implementation(Deps.Compose.material_icons_core)
    implementation(Deps.Compose.foundation)

    implementation(Deps.Compose.hilt_navigation_compose)

    androidTestImplementation(Deps.Compose.Test.ui_test_junit4)
    debugImplementation(Deps.Compose.Test.ui_tooling)

    implementation(Deps.Hilt.hilt_android)
    kapt(Deps.Hilt.hilt_android_compiler)
    testImplementation(Deps.Hilt.hilt_android_testing)
    kaptTest(Deps.Hilt.hilt_android_compiler)
    androidTestImplementation(Deps.Hilt.hilt_android_testing)
    kaptAndroidTest(Deps.Hilt.hilt_android_compiler)

    implementation(Deps.Room.room_runtime)
    implementation(Deps.Room.room_ktx)
    kapt(Deps.Room.room_compiler)

    testImplementation(Deps.Test.junit)
    androidTestImplementation(Deps.Test.ext_junit)
    androidTestImplementation(Deps.Test.espresso_core)
    testImplementation(Deps.Test.mockito_core)
}

kapt {
    correctErrorTypes = true
}