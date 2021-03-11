package io.datalbry.precise.processor.kotlin

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import io.datalbry.precise.api.schema.SchemaAware
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.processor.kotlin.parser.TypeParser
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion

@SupportedSourceVersion(SourceVersion.RELEASE_8)
class SchemaAwareProcessor: SymbolProcessor {

    lateinit var codeGenerator: CodeGenerator
    lateinit var logger: KSPLogger

    override fun init(
        options: Map<String, String>,
        kotlinVersion: KotlinVersion,
        codeGenerator: CodeGenerator,
        logger: KSPLogger
    ) {
        this.logger = logger
        this.codeGenerator = codeGenerator
    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val parser = TypeParser(resolver, logger)
        val annotationModels = resolver.getSymbolsWithAnnotation(SchemaAware::class.java.canonicalName)

        val types = annotationModels
            .map { it.accept(parser, Unit) }
            .fold(emptySet<Type>()) { set, type -> set + type }

        return emptyList()
    }

    override fun finish() = Unit

    companion object {
        internal const val PACKAGE = ""
        internal const val SCHEMA_FILE = "META-INF/lbrary/schema.json"
    }
}
