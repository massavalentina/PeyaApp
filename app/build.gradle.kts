
        //id("com.google.dagger.hilt.android")
        //kotlin("kapt")
        //alias(libs.plugins.android.application)
        //alias(libs.plugins.kotlin.android)
        //alias(libs.plugins.kotlin.compose)

        plugins {
            id("com.android.application")
            id("org.jetbrains.kotlin.android")
            id("org.jetbrains.kotlin.kapt")
            id("com.google.dagger.hilt.android")
            id("org.jetbrains.kotlin.plugin.compose")
        }

        android {
            namespace = "com.example.peya_ecommerce_app"
            compileSdk = 35

            defaultConfig {
                applicationId = "com.example.peya_ecommerce_app"
                minSdk = 24
                targetSdk = 35
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

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_11
                targetCompatibility = JavaVersion.VERSION_11
            }
            kotlinOptions {
                jvmTarget = "11"
            }

            buildFeatures {
                compose = true
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.3" // Debe alinearse con el Compose BOM
            }
        }

        dependencies {
            implementation(libs.androidx.core.ktx)
            implementation(libs.androidx.lifecycle.runtime.ktx)
            implementation(libs.androidx.activity.compose)
            implementation(platform(libs.androidx.compose.bom))
            implementation(libs.androidx.ui)
            implementation(libs.androidx.ui.graphics)
            implementation(libs.androidx.ui.tooling.preview)
            implementation(libs.androidx.material3)
            testImplementation(libs.junit)
            androidTestImplementation(libs.androidx.junit)
            androidTestImplementation(libs.androidx.espresso.core)
            androidTestImplementation(platform(libs.androidx.compose.bom))
            androidTestImplementation(libs.androidx.ui.test.junit4)
            debugImplementation(libs.androidx.ui.tooling)
            debugImplementation(libs.androidx.ui.test.manifest)

            implementation("androidx.navigation:navigation-compose:2.7.3")
            implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
            implementation("androidx.compose.material:material:1.5.3")
            implementation("androidx.compose.material:material-icons-extended")
            implementation("io.coil-kt:coil-compose:2.4.0")

            // Room
            implementation("androidx.room:room-runtime:2.5.2")
            implementation("androidx.room:room-ktx:2.5.2")
            kapt("androidx.room:room-compiler:2.5.2")

            // Dagger Hilt
            implementation("com.google.dagger:hilt-android:2.56.2")
            kapt("com.google.dagger:hilt-android-compiler:2.56.2")
            implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

            // Retrofit
            implementation("com.squareup.retrofit2:retrofit:2.9.0")
            implementation("com.squareup.retrofit2:converter-gson:2.9.0")

            // Gson
            implementation("com.google.code.gson:gson:2.10.1")

            // OkHttp Logging
            implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

            // DataStore
            implementation("androidx.datastore:datastore-preferences:1.1.0-alpha04")
        }