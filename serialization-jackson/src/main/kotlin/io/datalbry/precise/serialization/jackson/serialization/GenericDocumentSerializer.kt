package io.datalbry.precise.serialization.jackson.serialization

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.core.util.getBasicFieldType
import io.datalbry.precise.core.util.isBasicFieldType
import java.util.*

class GenericDocumentSerializer: StdSerializer<Document>(Document::class.java) {

    override fun serialize(value: Document, gen: JsonGenerator, provider: SerializerProvider) {
        val jackson = jacksonObjectMapper()
        val docMap = mapOf(
            "id" to value.id,
            "type" to value.type,
            "fields" to value.fields.toMap()
        )
        jackson.writeValue(gen, docMap)
    }

    private fun Set<Field<*>>.toMap(): Map<String, Any> {
        return this.mapNotNull { fieldToPair(it) }.toMap()
    }

    private fun fieldToPair(field: Field<*>): Pair<String, Any>? {
        val value = field.value
        return when {
            value == null -> null
            isBasicFieldType(value) -> field.name to getBasicFieldValue(value)
            value is Optional<*> -> value.map { fieldToPair(GenericField(field.name, it)) }.orElseGet { null }
            value is Array<*> -> field.name to value.mapNotNull { fieldToPair(GenericField(field.name, it)) }.map { it.second }.toSet()
            value is Collection<*> -> field.name to value.mapNotNull { fieldToPair(GenericField(field.name, it)) }.map { it.second }.toSet()
            value is Record -> field.name to value.fields.mapNotNull { fieldToPair(it) }.toMap()
            else -> throw IllegalArgumentException("Type ${value.javaClass.simpleName} is not supported by Precise.")
        }
    }

    private fun getBasicFieldValue(value: Any): Any {
        return when(getBasicFieldType(value)) {
            BasicFieldType.STRING -> value
            BasicFieldType.INT -> value
            BasicFieldType.LONG -> value
            BasicFieldType.FLOAT -> value
            BasicFieldType.DOUBLE -> value
            BasicFieldType.BOOLEAN -> value
            BasicFieldType.BYTE -> value
        }
    }
}
