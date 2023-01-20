import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
}

group = "mario.sebastian"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.litote.kmongo:kmongo-async:4.7.2")
    implementation("org.litote.kmongo:kmongo-coroutine:4.7.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.4")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    // BCrypt
    implementation("com.ToxicBakery.library.bcrypt:bcrypt:1.0.9")
    //Koin
    implementation("io.insert-koin:koin-core:$3.2.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}