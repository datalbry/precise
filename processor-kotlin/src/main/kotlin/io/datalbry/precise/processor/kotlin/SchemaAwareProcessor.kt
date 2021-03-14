package io.datalbry.precise.processor.kotlin

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.KSAnnotated
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.SchemaAware
import io.datalbry.precise.api.schema.field.BasicFieldTypes
import io.datalbry.precise.api.schema.type.DocumentType
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.processor.kotlin.deserializer.JacksonSchemaDeserializer
import io.datalbry.precise.processor.kotlin.visitor.FindTypesVisitor
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion

/**
 * [SchemaAwareProcessor] is a [SymbolProcessor] able to generate a [Schema],
 * by scanning for the [SchemaAware] annotation.
 *
 * Currently supporting [DocumentType] and [io.datalbry.precise.api.schema.type.EnumType].
 * The schema will be persisted in [SCHEMA_DIR]/[SCHEMA_FILE].
 *
 * @author timo gruen - 2021-03-11
 */
@SupportedSourceVersion(SourceVersion.RELEASE_8)
class SchemaAwareProcessor: SymbolProcessor {

    private val schemaDeserializer = JacksonSchemaDeserializer()
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
        val typeVisitor = FindTypesVisitor(logger, resolver)

        val annotationModels = resolver.getSymbolsWithAnnotation(SchemaAware::class.java.canonicalName)

        val types = getTypes(annotationModels, typeVisitor)
        val schema = Schema(types)

        validateSchema(schema)
        writeSchema(schema)
        return emptyList()
    }

    private fun getTypes(annotationModels: List<KSAnnotated>, typeVisitor: FindTypesVisitor): Set<Type> {
        return annotationModels
            .map { it.accept(typeVisitor, Unit) }
            .fold(emptySet()) { set, type -> set + type }
    }

    private fun validateSchema(schema: Schema) {
        val types = schema.types.map { it.name }
        val valid = schema
            .types
            .filterIsInstance<DocumentType>()
            .all { containsOnlyValidFields(it, types) }
        if (!valid) {
            val message = "Schema contains types without definition.Please note, that all fields of " +
                    "${SchemaAware::class.java.simpleName} Annotated classes have to be well-known types or derived " +
                    "from other ${SchemaAware::class.java.simpleName} Annotated classes."
            logger.error(message)
            throw IllegalArgumentException(message)
        }
    }

    private fun containsOnlyValidFields(type: DocumentType, types: List<String>): Boolean {
        return type.fields.all { field ->
            BasicFieldTypes
                .values()
                .map { it.id }
                .contains(field.type) || types.contains(field.type)
        }
    }

    private fun writeSchema(schema: Schema) {
        if (schema.types.isNotEmpty()) {
            SCHEMA_FILE.lastIndexOf("/")
            val schemaFile = codeGenerator.createNewFile(
                Dependencies.ALL_FILES,
                SCHEMA_DIR,
                SCHEMA_FILE.split(".")[0],
                SCHEMA_FILE.split(".")[1]
            )
            schemaDeserializer.writeSchema(schemaFile, schema)
        }
    }

    override fun finish() = Unit

    companion object {
        internal const val SCHEMA_DIR = "META-INF/datalbry"
        internal const val SCHEMA_FILE = "schema.json"
    }
}
