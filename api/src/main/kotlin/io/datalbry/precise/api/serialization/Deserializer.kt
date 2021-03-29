package io.datalbry.precise.api.serialization

import io.datalbry.precise.api.schema.Schema
import java.io.File

interface Deserializer<Type> {

    /**
     * Deserialize a [File] to an instance of type [Type]
     *
     * @param schema to use for deserialization
     * @param file to deserialize
     *
     * @return deserialized [Type]
     */
    fun read(schema: Schema, file: File): Type

    /**
     * Deserialize a raw String to an instance of type [Type]
     *
     * @param schema to use for deserialization
     * @param json to deserialize
     *
     * @return deserialized [Type]
     */
    fun read(schema: Schema, json: String): Type

    /**
     * Deserialize a raw ByteArray to an instance of type [Type]
     *
     * @param schema to use for deserialization
     * @param rawJson in form of a ByteArray
     *
     * @return deserialized [Type]
     */
    fun read(schema: Schema, rawJson: ByteArray): Type

}
