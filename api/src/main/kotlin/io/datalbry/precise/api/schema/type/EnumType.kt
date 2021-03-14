package io.datalbry.precise.api.schema.type

/**
 * The [EnumType] is a pre-defined [Type]
 *
 * It consists of any number of values and it's unique identifier
 *
 * @param name which is being used as a identifier. Has to be unique for all defined fields
 * @param values of the enum
 *
 * @author timo gruen - 2021-03-11
 */
data class EnumType(
    override val name: String,
    val values: Set<String>
) : Type {
    override val type = Types.ENUM
}
