plugins {
    kotlin("jvm")
    application
}

group = "dev.ssch"
version = "1.0"

repositories {
    mavenCentral()
}

application {
    mainClass = "dev.ssch.photobook.dsl.PhotoBookGeneratorKt"
    tasks.run.get().workingDir = rootProject.projectDir
}

dependencies {
    implementation("org.apache.pdfbox:pdfbox:3.0.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
