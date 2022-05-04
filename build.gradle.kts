buildscript {
}   //  Top-level build file where you can add configuration options common to all sub-projects/modules.

plugins {
    id(Config.com_android_application) version (Config.android) apply false
    id(Config.com_android_library) version (Config.android) apply false
    id(Config.org_jetbrains_kotlin_android) version (Config.kotlin) apply false
    id(Config.com_google_dagger_hilt_android) version (Config.hilt) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}