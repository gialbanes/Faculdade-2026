This is a Kotlin Multiplatform project targeting Android, Server.

* [/composeApp](./composeApp/src) is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - [commonMain](./composeApp/src/commonMain/kotlin) is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    the [iosMain](./composeApp/src/iosMain/kotlin) folder would be the right place for such calls.
    Similarly, if you want to edit the Desktop (JVM) specific part, the [jvmMain](./composeApp/src/jvmMain/kotlin)
    folder is the appropriate location.

* [/server](./server/src/main/kotlin) is for the Ktor server application.

* [/shared](./shared/src) is for the code that will be shared between all targets in the project.
  The most important subfolder is [commonMain](./shared/src/commonMain/kotlin). If preferred, you
  can add code to the platform-specific folders here too.

### Build and Run Android Application

To build and run the development version of the Android app, use the run configuration from the run widget
in your IDE’s toolbar or build it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :composeApp:assembleDebug
  ```
- on Windows
  ```shell
  .\gradlew.bat :composeApp:assembleDebug
  ```

### Build and Run Server

To build and run the development version of the server, use the run configuration from the run widget
in your IDE’s toolbar or run it directly from the terminal:
- on macOS/Linux
  ```shell
  ./gradlew :server:run
  ```
- on Windows
  ```shell
  .\gradlew.bat :server:run
  ```

---

# Catalog  `gradle/libs.versions.toml`

```
ktor-server-swagger = { module = "io.ktor:ktor-server-swagger-jvm", version.ref = "ktor" }
ktor-server-status-pages = { module = "io.ktor:ktor-server-status-pages-jvm", version.ref = "ktor" }
```

# Catalog  `server/build.gradle.kts`

```
implementation(libs.ktor.server.swagger)
implementation(libs.ktor.server.status.pages)
```


# Novas dependências 


gradle/libs.versions.toml
```
[versions]
# ...
supabaseKt = "3.0.1"

[libraries]
# ...
ktor-client-cio-jvm = { module = "io.ktor:ktor-client-cio-jvm", version.ref = "ktor" }
supabase-postgrest-kt = { module = "io.github.jan-tennert.supabase:postgrest-kt", version.ref = "supabaseKt" }
```

server/build.gradle.kts
```
dependencies {
    // === Semana 04: Supabase Client para Kotlin ===
    implementation(libs.supabase.postgrest.kt)
    implementation(libs.ktor.client.cio.jvm)
}
```