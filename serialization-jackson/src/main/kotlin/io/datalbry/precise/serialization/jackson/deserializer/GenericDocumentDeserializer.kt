package io.datalbry.precise.serialization.jackson.deserializer

import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.deser.std.StdNodeBasedDeserializer
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.generic.GenericDocument

/**
 * [StdDeserializer] implementation to deserialize JSON to [Document]
 *
 * @author timo gruen - 2021-03-16
 */
class GenericDocumentDeserializer(schema: Schema) : StdNodeBasedDeserializer<Document>(Document::class.java) {

    private val recordDeserializer = GenericRecordDeserializer(schema)

    override fun convert(node: JsonNode, ctxt: DeserializationContext): Document {
        val id = node.get("id").asText()
        val record = recordDeserializer.convert(node, ctxt)
        return GenericDocument(id, record)
    }
}
