package io.datalbry.precise.api.schema.document.generic

import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record

data class GenericDocument(
    override val type: String,
    override val id: String,
    override val fields: Set<Field<*>>
) : Document {

    override fun get(key: String) = fields.first { it.name == key }

    override fun getKeys() = fields.map { it.name }.toSet()

}
