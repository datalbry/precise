package io.datalbry.precise.api.schema.type

import io.datalbry.precise.api.schema.field.Field

/**
 * The [DocumentType] is a pre-defined [Type]
 *
 * It consists of any number of [Field]s and it's unique identifier
 *
 * @param name which is being used as a identifier. Has to be unique for all defined fields
 * @param fields describing the [DocumentType]
 *
 * @author timo gruen - 2021-03-11
 */
data class DocumentType(
    override val name: String,
    val fields: Set<Field>
) : Type {
    override val type = Types.DOCUMENT
}
