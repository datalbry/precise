package io.datalbry.precise.serialization.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.api.serialization.RecordDeserializer
import java.io.File

class JacksonRecordDeserializer: RecordDeserializer {

    override fun read(schema: Schema, file: File): Record {
        val jackson = preconfiguredJackson(schema)
        return jackson.readValue(file)
    }

    override fun read(schema: Schema, json: String): Record {
        val jackson = preconfiguredJackson(schema)
        return jackson.readValue(json)
    }

    override fun read(schema: Schema, rawJson: ByteArray): Record {
        val jackson = preconfiguredJackson(schema)
        return jackson.readValue(rawJson)
    }

    private fun preconfiguredJackson(schema: Schema): ObjectMapper {
        val jackson = jacksonObjectMapper()
        val preciseModule = PreciseModule(schema)
        return jackson.registerModule(preciseModule)
    }
}
