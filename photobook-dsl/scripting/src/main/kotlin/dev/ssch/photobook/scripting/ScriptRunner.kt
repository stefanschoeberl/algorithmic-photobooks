package dev.ssch.photobook.scripting

import dev.ssch.photobook.dsl.Book
import dev.ssch.photobook.dsl.BookBuilder
import dev.ssch.photobook.dsl.renderBook
import java.io.File
import kotlin.script.experimental.api.ScriptDiagnostic
import kotlin.script.experimental.api.implicitReceivers
import kotlin.script.experimental.host.toScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

fun BookBuilder.toBook(): Book {
    return Book(this.pages)
}

fun evalFile(scriptFile: File) {
    val bookBuilder = BookBuilder()

    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<PhotoBookScript>()
    val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<PhotoBookScript> {
        implicitReceivers(bookBuilder)
    }
    val result = BasicJvmScriptingHost().eval(scriptFile.toScriptSource(), compilationConfiguration, evaluationConfiguration)
    result.reports.forEach {
        if (it.severity > ScriptDiagnostic.Severity.DEBUG) {
            println(" : ${it.message}" + if (it.exception == null) "" else ": ${it.exception}")
        }
    }

    val book = bookBuilder.toBook()
    renderBook(book, scriptFile.path.removeSuffix(".book.kts") + ".pdf")
}

fun main(vararg args: String) {
    if (args.size != 1) {
        println("usage: <app> <script file>")
    } else {
        val scriptFile = File(args[0])
        evalFile(scriptFile)
    }
}
