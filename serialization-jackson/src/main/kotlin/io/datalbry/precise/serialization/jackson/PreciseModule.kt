package io.datalbry.precise.serialization.jackson

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.serialization.jackson.deserializer.GenericDocumentDeserializer
import io.datalbry.precise.serialization.jackson.deserializer.GenericRecordDeserializer
import io.datalbry.precise.serialization.jackson.deserializer.SchemaDeserializer

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
        val documentDeserializer = GenericDocumentDeserializer(schema)
        val recordDeserializer = GenericRecordDeserializer(schema)
        val schemaDeserializer = SchemaDeserializer()
        this.addDeserializer(Document::class.java, documentDeserializer)
        this.addDeserializer(Record::class.java, recordDeserializer)
        this.addDeserializer(Schema::class.java, schemaDeserializer)
    }

}
