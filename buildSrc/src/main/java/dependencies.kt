object Config {
    const val compile_target_sdk = 32
    const val min_sdk = 30

    const val version_code = 1
    const val version_name = "1.0.0"

    const val compose_version = "1.1.1"

    const val kotlin = "1.6.10"
    const val android = "7.1.3"
    const val hilt = "2.41"

    const val com_android_application = "com.android.application"
    const val com_android_library = "com.android.library"
    const val org_jetbrains_kotlin_android = "org.jetbrains.kotlin.android"
    const val com_google_dagger_hilt_android = "com.google.dagger.hilt.android"
}

object Deps {

    object CoreKTX {
        private const val version = "1.7.0"
        val core_ktx by lazy { "androidx.core:core-ktx:$version" }
    }

    object Lifecycle {
        private const val version = "2.4.1"
        val lifecycle_runtime_ktx by lazy { "androidx.lifecycle:lifecycle-runtime-ktx:$version" }
        val lifecycle_viewmodel_compose by lazy { "androidx.lifecycle:lifecycle-viewmodel-compose:$version" }
        val lifecycle_viewmodel_ktx by lazy { "androidx.lifecycle:lifecycle-viewmodel-ktx:$version" }
    }

    object ActivityCompose {
        private const val version = "1.4.0"
        val activity_compose by lazy { "androidx.activity:activity-compose:$version" }
    }

    object Hilt {
        private const val version = "2.40.5"
        val hilt_android by lazy { "com.google.dagger:hilt-android:$version" }
        val hilt_android_compiler by lazy { "com.google.dagger:hilt-android-compiler:$version" }
        val hilt_android_testing by lazy { "com.google.dagger:hilt-android-testing:$version" }
    }

    object Compose {
        val ui by lazy { "androidx.compose.ui:ui:${Config.compose_version}" }
        val ui_tooling_preview by lazy { "androidx.compose.ui:ui-tooling-preview:${Config.compose_version}" }

        val material by lazy { "androidx.compose.material:material:${Config.compose_version}" }
        val material_icons_core by lazy { "androidx.compose.material:material-icons-core:${Config.compose_version}" }
        val foundation by lazy { "androidx.compose.foundation:foundation:${Config.compose_version}" }

        private const val hilt_navigation_compose_version = "1.0.0"
        val hilt_navigation_compose by lazy { "androidx.hilt:hilt-navigation-compose:$hilt_navigation_compose_version" }

        object Test {
            val ui_test_junit4 by lazy { "androidx.compose.ui:ui-test-junit4:${Config.compose_version}" }
            val ui_tooling by lazy { "androidx.compose.ui:ui-tooling:${Config.compose_version}" }
        }
    }

    object Room {
        private const val version = "2.4.2"
        const val room_runtime = "androidx.room:room-runtime:$version"
        const val room_ktx = "androidx.room:room-ktx:$version"
        const val room_compiler = "androidx.room:room-compiler:$version"
    }

    object Test {
        private const val junit_version = "4.13.2"
        val junit by lazy { "junit:junit:$junit_version" }

        private const val ext_junit_version = "1.1.3"
        val ext_junit by lazy { "androidx.test.ext:junit:$ext_junit_version" }

        private const val espresso_core_version = "3.4.0"
        val espresso_core by lazy { "androidx.test.espresso:espresso-core:$espresso_core_version" }

        private const val mockito_core_version = "3.11.2"
        val mockito_core by lazy { "org.mockito:mockito-core:$mockito_core_version" }
    }
}
