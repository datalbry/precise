package io.datalbry.precise.serialization.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.serialization.DocumentDeserializer
import io.datalbry.precise.serialization.jackson.PreciseModule
import java.io.File

/**
 * Jackson specific implementation for the [DocumentDeserializer]
 *
 * NOTE:
 * Especially when using DI frameworks, such as Spring, one is using a global jackson instance which
 * can be preconfigured with jackson modules.
 *
 * @see PreciseModule for native jackson support
 *
 * @author timo gruen - 2021-03-17
 */
class JacksonDocumentDeserializer: DocumentDeserializer {

    override fun read(schema: Schema, file: File): Document {
        val jackson = preconfiguredJackson(schema)
        return jackson.readValue(file)
    }

    override fun read(schema: Schema, json: String): Document {
        val jackson = preconfiguredJackson(schema)
        return jackson.readValue(json)
    }

    override fun read(schema: Schema, rawJson: ByteArray): Document {
        val jackson = preconfiguredJackson(schema)
        return jackson.readValue(rawJson)
    }

    private fun preconfiguredJackson(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        val preciseModule = PreciseModule(schema)
        return jackson.registerModule(preciseModule)
    }
}
