package dev.ssch.photobook.scripting


import dev.ssch.photobook.dsl.BookBuilder
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromCurrentContext
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = "book.kts",
    compilationConfiguration = PhotoBookScriptConfiguration::class
)
abstract class PhotoBookScript

object PhotoBookScriptConfiguration: ScriptCompilationConfiguration({
    implicitReceivers(BookBuilder::class)
    jvm {
        dependenciesFromCurrentContext(wholeClasspath = true)
    }
    compilerOptions.append("-Xadd-modules=ALL-MODULE-PATH")
})
