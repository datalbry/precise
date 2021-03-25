package io.datalbry.precise.serialization.jackson.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document

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
        TODO()
    }
}
