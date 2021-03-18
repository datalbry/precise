package io.datalbry.precise.api.serialization

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import java.io.File

/**
 * [DocumentDeserializer] is a basic interface for [Document] serialization
 *
 * It might only act as a entry point if one is not basically using a standard JSON framework such as Jackson,
 * Gson and others.
 *
 * @author timo gruen - 2021-03-17
 */
interface DocumentDeserializer {

    /**
     * Deserialize a [File] to a [Document]
     *
     * @param schema to use for deserialization
     * @param file to deserialize
     *
     * @return deserialized [Document]
     */
    fun read(schema: Schema, file: File): Document

    /**
     * Deserialize a raw String to a [Document]
     *
     * @param schema to use for deserialization
     * @param json to deserialize
     *
     * @return deserialized [Document]
     */
    fun read(schema: Schema, json: String): Document

    /**
     * Deserialize a raw ByteArray to a [Document]
     *
     * @param schema to use for deserialization
     * @param rawJson in form of a ByteArray
     *
     * @return deserialized [Document]
     */
    fun read(schema: Schema, rawJson: ByteArray): Document

}
