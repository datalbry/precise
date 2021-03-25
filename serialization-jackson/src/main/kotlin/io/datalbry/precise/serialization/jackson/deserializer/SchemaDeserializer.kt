package io.datalbry.precise.serialization.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.field.Field
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.serialization.jackson.extension.mapValues

/**
 * Jackson specific [StdDeserializer] for [Schema] deserialization
 *
 * @author timo gruen - 2021-03-16
 */
class SchemaDeserializer: StdDeserializer<Schema>(Schema::class.java) {

    override fun deserialize(parser: JsonParser, ctxt: DeserializationContext): Schema {
        val node = parser.codec.readTree<JsonNode>(parser)
        val types = node.get("types").map(this::toType).toSet()
        return Schema(types)
    }

    private fun toType(node: JsonNode): Type {
        return when (node.get("type").asText()) {
            "DOCUMENT" -> toDocumentType(node)
            "ENUM" -> toEnumType(node)
            else -> throw IllegalArgumentException("Type may only be DOCUMENT or ENUM")
        }
    }

    private fun toDocumentType(node: JsonNode): Type {
        val name = node.get("name").asText()
        val fields = node.mapValues("fields", this::toDocumentField)
        return RecordType(name, fields)
    }

    private fun toDocumentField(node: JsonNode): Field {
        val name = node.get("name").asText()
        val type = node.get("type").asText()
        val multiValue = node.get("multiValue").asBoolean()
        val optional = node.get("optional").asBoolean()
        return Field(name, type, multiValue, optional)
    }

    private fun toEnumType(node: JsonNode): Type {
        val name = node.get("name").asText()
        val values = node.findValuesAsText("values").toSet()
        return EnumType(name, values)
    }
}
