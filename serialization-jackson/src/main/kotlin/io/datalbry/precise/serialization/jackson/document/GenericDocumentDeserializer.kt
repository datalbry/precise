package io.datalbry.precise.serialization.jackson.document

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.serialization.jackson.schema.exception.SchemaTypeNotFound

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
        val typeSchema = schema.types.firstOrNull { it.name == type } ?: throw SchemaTypeNotFound(type)

        val fields = node.get("fields").elements()
        TODO()
    }

}
