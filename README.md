# BasicMVVMSetup

A basic Android project pre-configured with everything needed for an MVVM (Model–View–ViewModel) architecture using Jetpack Compose.

All dependencies are managed through the Gradle version catalog: [gradle/libs.versions.toml](gradle/libs.versions.toml).

## Tech Stack

- **Kotlin** 2.2.10 (built-in Kotlin from AGP 9.2.1)
- **Jetpack Compose** — Compose BOM `2026.02.01`, Material 3
- **compileSdk** 37 / **minSdk** 24

## Dependencies Added

### MVVM / Lifecycle
| Library | Version | Purpose |
|---|---|---|
| `androidx.lifecycle:lifecycle-viewmodel-compose` | 2.11.0 | `viewModel()` composable to obtain ViewModels in Compose |
| `androidx.lifecycle:lifecycle-runtime-ktx` | 2.11.0 | Lifecycle-aware coroutine scopes (`lifecycleScope`, `repeatOnLifecycle`) |

### Navigation (Compose)
| Library | Version | Purpose |
|---|---|---|
| `androidx.navigation:navigation-compose` | 2.9.8 | `NavHost` / `NavController` based navigation between composable screens |

### Dependency Injection — Hilt
| Library | Version | Purpose |
|---|---|---|
| `com.google.dagger:hilt-android` | 2.59.2 | Hilt runtime |
| `com.google.dagger:hilt-compiler` (via KSP) | 2.59.2 | Annotation processor generating the DI graph |
| `androidx.hilt:hilt-navigation-compose` | 1.4.0 | `hiltViewModel()` scoped to navigation back-stack entries |

Plugins applied: `com.google.dagger.hilt.android` (2.59.2) and `com.google.devtools.ksp` (2.3.10).

### Networking — Retrofit
| Library | Version | Purpose |
|---|---|---|
| `com.squareup.retrofit2:retrofit` | 3.0.0 | Type-safe HTTP client |
| `com.squareup.retrofit2:converter-gson` | 3.0.0 | Gson converter for JSON (de)serialization |
| `com.squareup.okhttp3:okhttp` | 5.4.0 | Underlying HTTP engine |
| `com.squareup.okhttp3:logging-interceptor` | 5.4.0 | Logs network requests/responses for debugging |

### Image Loading — Coil
| Library | Version | Purpose |
|---|---|---|
| `io.coil-kt.coil3:coil-compose` | 3.4.0 | `AsyncImage` composable for loading images |
| `io.coil-kt.coil3:coil-network-okhttp` | 3.4.0 | Network fetching for Coil via OkHttp |

### Testing

Dependencies cover all three test types. Which source set a dependency lives in determines where the test runs.

#### Unit tests — `src/test/` (JVM, host-side, run via `./gradlew testDebugUnitTest`)
| Library | Version | Purpose |
|---|---|---|
| `junit:junit` | 4.13.2 | JUnit4 test framework |
| `org.jetbrains.kotlinx:kotlinx-coroutines-test` | 1.11.0 | `runTest`, test dispatchers for coroutine/ViewModel testing |
| `app.cash.turbine:turbine` | 1.2.1 | Testing Kotlin `Flow` emissions (StateFlow/SharedFlow) |
| `io.mockk:mockk` | 1.14.3 | Kotlin mocking framework |
| `org.robolectric:robolectric` | 4.16.1 | Run Android/Compose UI tests host-side without a device |
| `androidx.test.ext:junit` | 1.3.0 | AndroidX JUnit runner/rules for Robolectric |
| `androidx.compose.ui:ui-test-junit4` | (Compose BOM) | Compose UI test APIs (usable host-side via Robolectric) |

#### Instrumented tests — `src/androidTest/` (device/emulator, run via `./gradlew connectedDebugAndroidTest`)
| Library | Version | Purpose |
|---|---|---|
| `androidx.compose.ui:ui-test-junit4` | (Compose BOM) | Compose UI interaction/assertion APIs |
| `androidx.test.espresso:espresso-core` | 3.7.0 | Espresso (for any View-based UI) |
| `androidx.test.ext:junit` | 1.3.0 | AndroidX JUnit integration |
| `androidx.test:runner` | 1.7.0 | Instrumentation test runner |
| `androidx.test:rules` | 1.7.0 | Test rules (`ActivityScenarioRule`, etc.) |
| `androidx.navigation:navigation-testing` | 2.9.8 | `TestNavHostController` for navigation tests |
| `com.google.dagger:hilt-android-testing` | 2.59.2 | Hilt test graph (`@HiltAndroidTest`, `HiltAndroidRule`) — compiler applied via `kspAndroidTest` |
| `io.mockk:mockk-android` | 1.14.3 | MockK on-device |

A custom [`HiltTestRunner`](app/src/androidTest/java/com/silentcreator/basicmvvmsetup/HiltTestRunner.kt) swaps in `HiltTestApplication`; it's wired via `testInstrumentationRunner` in [app/build.gradle.kts](app/build.gradle.kts).

#### Screenshot tests — `src/screenshotTest/` (Compose Preview Screenshot Testing, host-side)
| Library / Plugin | Version | Purpose |
|---|---|---|
| `com.android.compose.screenshot` (plugin) | 0.0.1-alpha15 | Google's first-party Compose preview screenshot testing tool |
| `com.android.tools.screenshot:screenshot-validation-api` | 0.0.1-alpha15 | `@PreviewTest` annotation + validation API |
| `androidx.compose.ui:ui-tooling` | (Compose BOM) | Renders `@Preview` composables for screenshotting |

Enabled by `android.experimental.enableScreenshotTest=true` in [gradle.properties](gradle.properties) plus the matching `experimentalProperties` flag in the module build file.

- Generate/update reference images: `./gradlew updateDebugScreenshotTest` (stored in `app/src/screenshotTestDebug/reference/`)
- Validate against references: `./gradlew validateDebugScreenshotTest` (HTML report under `app/build/reports/screenshotTest/`)

Write screenshot tests by putting `@PreviewTest @Preview @Composable` functions in `app/src/screenshotTest/java/...`.

> **Note:** Compose Preview Screenshot Testing is still in **alpha**. If you later want device-rendered screenshots (edge-to-edge, system UI), add [Dropshots](https://github.com/dropbox/dropshots) as an instrumented alternative.

## Version Notes

- **Hilt 2.59.2 and KSP 2.3.10** are the latest versions and are also the minimum lines required for AGP 9 compatibility.
- **Coil is pinned to 3.4.0** (not 3.5.0): Coil 3.5.0 is compiled with Kotlin 2.4.0, whose metadata cannot be read by the Kotlin 2.2.10 compiler that AGP 9.2.1 embeds. Bump Coil when the project's AGP/Kotlin moves to 2.4+.
- **compileSdk was raised from 36 to 37**, required by the latest androidx.core / lifecycle / hilt artifacts.

## Next Steps (not yet wired up)

1. Add the `INTERNET` permission to `AndroidManifest.xml` for Retrofit/Coil:
   ```xml
   <uses-permission android:name="android.permission.INTERNET" />
   ```
2. Create an `Application` class annotated with `@HiltAndroidApp` and register it in the manifest.
3. Annotate `MainActivity` with `@AndroidEntryPoint`.
4. Provide a `Retrofit` instance from a Hilt `@Module`, and inject repositories into `@HiltViewModel` ViewModels obtained via `hiltViewModel()`.
