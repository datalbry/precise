package io.datalbry.precise.api.schema.field

/**
 * [Field] holds the information about a specific field of an [io.datalbry.precise.api.schema.type.DocumentType]
 *
 * @param name of the field - requires to be unique for the type
 * @param type of the field
 * @param multiValue if the field could contain any arbitrary amount of values
 * @param optional if the field can be omitted (ignored if multiValue=true)
 *
 * @author timo gruen - 2021-03-11
 */
data class Field(
    val name: String,
    val type: String,
    val multiValue: Boolean = false,
    val optional: Boolean = false
)
