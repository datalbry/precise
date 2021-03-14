package io.datalbry.precise.processor.kotlin.deserializer

import io.datalbry.precise.api.schema.Schema
import java.io.File
import java.io.OutputStream

/**
 * The [SchemaDeserializer] is able to serialize a [Schema] to a JSON file.
 *
 * @author timo gruen - 2021-03-11
 */
interface SchemaDeserializer {

    /**
     * Writes a [Schema] to the desired location [File]
     *
     * The schema will always be deserialized to JSON
     */
    fun writeSchema(location: OutputStream, schema: Schema)

}
