package io.datalbry.precise.processor.kotlin.deserializer

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.datalbry.precise.api.schema.Schema
import java.io.OutputStream

/**
 * [SchemaDeserializer] implementation backed by Jackson
 *
 * @author timo gruen - 2021-03-11
 */
class JacksonSchemaDeserializer: SchemaDeserializer {

    private val jackson = jacksonObjectMapper()


    override fun writeSchema(location: OutputStream, schema: Schema) {
        jackson.enable(SerializationFeature.INDENT_OUTPUT);
        jackson.writeValue(location, schema)
    }
}
