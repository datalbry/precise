package io.datalbry.precise.serialization.jackson.document

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document

private fun version() = Version(1, 0, 0, null, "io.datalbry.precise", "serialization-jackson")

/**
 * [PreciseModule] enables Jackson to deserialize [Document]s using the corresponding [Schema]
 *
 * NOTE:
 * The deserialization process does not take care of validating the actual [Document]
 * It only takes care of deserialization, which may fail if a field key is not present in the [Schema]
 *
 * @param schema to use for deserialization
 *
 * @author timo gruen - 2021-03-16
 */
class PreciseModule(schema: Schema): SimpleModule("Precise", version()) {

    init {
        val deserializer = GenericDocumentDeserializer(schema)
        this.addDeserializer(Document::class.java, deserializer)
    }

}
