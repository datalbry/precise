package io.datalbry.precise.serialization.generic

import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field

class GenericDocument(
    override val type: String,
    private val fields: Set<Field<*>>
) : Document {

    override fun get(key: String) = fields.first { it.name == key }

    override fun getKeys() = fields.map { it.name }.toSet()

}
