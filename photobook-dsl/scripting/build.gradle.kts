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
    mainClass = "dev.ssch.photobook.scripting.ScriptRunnerKt"
    tasks.run.get().workingDir = rootProject.projectDir
}

dependencies {
    implementation(project(":dsl"))
    implementation("org.jetbrains.kotlin:kotlin-scripting-common")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies")
    implementation("org.jetbrains.kotlin:kotlin-scripting-dependencies-maven")
    implementation("org.jetbrains.kotlin:kotlin-scripting-jvm-host")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

tasks.register("allInOneJar", Jar::class) {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    manifest {
        attributes["Main-Class"] = "dev.ssch.photobook.scripting.ScriptRunnerKt"
    }
    archiveBaseName = "photobook-generator"
    from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) })
    with(tasks["jar"] as CopySpec)
}

tasks.assemble {
    dependsOn(tasks.named("allInOneJar"))
}
