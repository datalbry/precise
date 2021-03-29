package io.datalbry.precise.serialization.jackson.document

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.schema.document.generic.GenericDocument
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.precise.api.schema.document.generic.GenericRecord
import io.datalbry.precise.serialization.jackson.extension.*
import kotlin.collections.Map.Entry
import io.datalbry.precise.api.schema.field.Field as FieldSchema

/**
 * [StdDeserializer] implementation to deserialize JSON to [Document]
 *
 * @author timo gruen - 2021-03-16
 */
class GenericDocumentDeserializer(
    private val schema: Schema
)
    : StdDeserializer<Document>(Document::class.java)
{

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): Document {
        val node = parser.codec.readTree<JsonNode>(parser)
        val type = node.get("type").asText()
        val id = node.get("id").asText()

        val typeSchema = schema.typeSchema(type)
        if (typeSchema !is RecordType) throw IllegalArgumentException("Only documents can be deserialized, check the schema whether the Type[$type] is declared as DOCUMENT or not")
        val fields = getDocumentFields(schema, typeSchema, node.get("fields"))

        return GenericDocument(type, id, fields)
    }

    private fun getDocumentFields(schema: Schema, typeSchema: RecordType, node: JsonNode): Set<Field<*>> {
        return node.fields().asSequence().map {
            val fieldSchema = typeSchema.getFieldSchema(it.key)
            val type = fieldSchema.type
            when {
                isBasicFieldType(type) -> getBasicField(fieldSchema, it)
                schema.isEnumType(type) -> getEnumField(it)
                schema.isRecordType(type) -> getRecordField(schema, fieldSchema, it)
                else -> throw IllegalArgumentException("Type[$type] is not defined in the schema, nor well-known")
            }
        }.toSet()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun getRecordField(schema: Schema, type: FieldSchema, entry: Entry<String, JsonNode>): Field<*> {
        val recordType = schema.getRecordType(type.type)
        val records = when {
            type.multiValue -> entry.value
                .mapValues { getDocumentFields(schema, recordType, it) }
                .map { GenericRecord(type.name, it) }
            else -> GenericRecord(type.name, getDocumentFields(schema, recordType, entry.value))
        }
        return GenericField(entry.key, records)
    }

    private fun getEnumField(entry: Entry<String, JsonNode>): GenericField<*> {
        return GenericField(entry.key, entry.value.asText())
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun getBasicField(fieldSchema: FieldSchema, entry: Entry<String, JsonNode>): Field<*> {
        val type = basicFieldTypeById(fieldSchema.type)
        val node = entry.value
        val value = when {
            fieldSchema.multiValue -> node.mapValues { readValue(type, node) }
            else -> readValue(type, node)
        }
        return GenericField(entry.key, value!!)
    }

    private fun readValue(type: BasicFieldType, node: JsonNode): Any? {
        return when (type) {
            BasicFieldType.INT -> node.asInt()
            BasicFieldType.LONG -> node.asLong()
            BasicFieldType.FLOAT -> node.asFloat()
            BasicFieldType.DOUBLE -> node.asDouble()
            BasicFieldType.BYTE -> node.asInt().toUByte()
            BasicFieldType.STRING -> node.asText()
            BasicFieldType.BOOLEAN -> node.asBoolean()
        }
    }
}
