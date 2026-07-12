package com.silentcreator.basicmvvmsetup

import android.app.Application
import android.content.Context
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

/**
 * Custom [AndroidJUnitRunner] used for instrumented tests. It replaces the app's
 * Application with [HiltTestApplication] so Hilt can build the test dependency graph.
 *
 * Referenced from `app/build.gradle.kts` via `testInstrumentationRunner`.
 */
class HiltTestRunner : AndroidJUnitRunner() {
    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?,
    ): Application {
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
