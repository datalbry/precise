package io.datalbry.precise.serialization.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.deser.std.StdNodeBasedDeserializer
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.api.schema.document.generic.GenericField
import io.datalbry.precise.api.schema.document.generic.GenericRecord
import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.serialization.jackson.exception.InvalidTypeException
import io.datalbry.precise.serialization.jackson.exception.NoSuchTypeException
import io.datalbry.precise.serialization.jackson.extension.*
import io.datalbry.precise.api.schema.field.Field as FieldSchema

/**
 * Jackson specific [StdDeserializer] for [Record] deserialization
 *
 * @author timo gruen - 2021-03-25
 */
@ExperimentalUnsignedTypes
class GenericRecordDeserializer(
    private val schema: Schema
)
    : StdNodeBasedDeserializer<Record>(Record::class.java)
{

    override fun convert(node: JsonNode, ctxt: DeserializationContext): Record {
        val type = node.get("type").asText()

        val typeSchema = schema.typeSchema(type)
        if (typeSchema !is RecordType) throw InvalidTypeException(typeSchema, RecordType::class.java)
        val fields = getFields(schema, typeSchema, node.get("fields"))

        return GenericRecord(type, fields)
    }

    private fun getFields(schema: Schema, typeSchema: RecordType, node: JsonNode): Set<Field<*>> {
        return node.fields()
            .asSequence()
            .map {
                val fieldSchema = typeSchema.getFieldSchema(it.key)
                val type = fieldSchema.type
                when {
                    isBasicFieldType(type) -> getBasicField(fieldSchema, it)
                    schema.isEnumType(type) -> getEnumField(it)
                    schema.isRecordType(type) -> getRecordField(schema, fieldSchema, it)
                else -> throw NoSuchTypeException(schema, type)
                }
            }
            .toSet()
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun getRecordField(schema: Schema, type: FieldSchema, entry: Map.Entry<String, JsonNode>): Field<*> {
        val recordType = schema.getRecordType(type.type)
        val records = when {
            type.multiValue -> {
                entry.value
                    .mapValues { getFields(schema, recordType, it) }
                    .map { GenericRecord(type.type, it) }
            }
            else -> GenericRecord(type.type, getFields(schema, recordType, entry.value))
        }
        return GenericField(entry.key, records)
    }

    private fun getEnumField(entry: Map.Entry<String, JsonNode>): GenericField<*> {
        return GenericField(entry.key, entry.value.asText())
    }

    @Suppress("IMPLICIT_CAST_TO_ANY")
    private fun getBasicField(fieldSchema: FieldSchema, entry: Map.Entry<String, JsonNode>): Field<*> {
        val type = basicFieldTypeById(fieldSchema.type)
        val node = entry.value
        val value = when {
            fieldSchema.multiValue -> node.mapValues { readValue(type, it) }
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

