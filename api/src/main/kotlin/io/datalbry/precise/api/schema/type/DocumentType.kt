package io.datalbry.precise.api.schema.type

import io.datalbry.precise.api.schema.field.Field

data class DocumentType(
    override val name: String,
    val fields: Set<Field>
) : Type {
    override val type = Types.DOCUMENT
}
