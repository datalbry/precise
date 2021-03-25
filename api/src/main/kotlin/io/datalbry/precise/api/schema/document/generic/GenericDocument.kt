package io.datalbry.precise.api.schema.document.generic

import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Record

class GenericDocument(
    override val id: String,
    private val record: Record
) : Document {

    override val type: String = record.type

    override fun get(key: String) = record[key]

    override fun getKeys() = record.getKeys()

    override fun getFields() = record.getFields()

}
