package io.datalbry.precise.processor.kotlin.util

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.symbolProcessors
import io.datalbry.precise.processor.kotlin.SchemaAwareProcessor
import java.io.File
import java.nio.file.Paths


fun compile(vararg sourceFiles: SourceFile): KotlinCompilation.Result {
    return KotlinCompilation().apply {
        sources = sourceFiles.asList()
        symbolProcessors = listOf(SchemaAwareProcessor())
        inheritClassPath = true
        messageOutputStream = System.out
    }.compile()
}

inline fun <reified T> getTestSchema(name: String): File {
    val resource = T::class.java.getResource("/schema/${name}")
    return Paths.get(resource.toURI()).toFile()
}

fun KotlinCompilation.Result.getSchemaFile(): File {
    return this.generatedFiles.first { it.absolutePath.contains(SchemaAwareProcessor.SCHEMA_FILE) }
}

