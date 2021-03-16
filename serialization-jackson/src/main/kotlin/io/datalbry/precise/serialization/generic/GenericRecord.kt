package io.datalbry.precise.serialization.generic

import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record

class GenericRecord(
    private val fields: Set<Field<*>>
): Record {

    override fun get(key: String) = fields.first { it.name == key }

}
