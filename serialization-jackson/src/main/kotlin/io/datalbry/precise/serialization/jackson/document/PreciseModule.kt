package io.datalbry.precise.serialization.jackson.document

import com.fasterxml.jackson.core.Version
import com.fasterxml.jackson.databind.module.SimpleModule
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document

private fun version() = Version(1, 0, 0, null, "io.datalbry.precise", "serialization-jackson")

/**
 *
 */
class PreciseModule(schema: Schema): SimpleModule("Precise", version()) {

    init {
        val deserializer = GenericDocumentDeserializer(schema)
        this.addDeserializer(Document::class.java, deserializer)
    }

}
