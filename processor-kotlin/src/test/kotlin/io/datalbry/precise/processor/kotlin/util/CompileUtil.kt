package io.datalbry.precise.processor.kotlin.util

import com.tschuchort.compiletesting.KotlinCompilation
import com.tschuchort.compiletesting.SourceFile
import com.tschuchort.compiletesting.kspSourcesDir
import com.tschuchort.compiletesting.symbolProcessorProviders
import io.datalbry.precise.processor.kotlin.SchemaAwareProcessor.Companion.SCHEMA_DIR
import io.datalbry.precise.processor.kotlin.SchemaAwareProcessor.Companion.SCHEMA_FILE
import io.datalbry.precise.processor.kotlin.SchemaAwareProcessorProvider
import java.io.File
import java.nio.file.Paths


fun compile(vararg sourceFiles: SourceFile): Pair<File, KotlinCompilation.Result> {
    val compilation = KotlinCompilation().apply {
        sources = sourceFiles.asList()
        symbolProcessorProviders = listOf(SchemaAwareProcessorProvider())
        inheritClassPath = true
        messageOutputStream = System.out
    }
    return compilation.kspSourcesDir to compilation.compile()
}

inline fun <reified T> getTestSchema(name: String): File {
    val resource = T::class.java.getResource("/schema/${name}")
    return Paths.get(resource.toURI()).toFile()
}

fun Pair<File, KotlinCompilation.Result>.getSchemaFile(): File {
    return File(this.first, "resources/$SCHEMA_DIR/$SCHEMA_FILE")
}

