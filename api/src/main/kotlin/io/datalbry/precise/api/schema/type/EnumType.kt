package io.datalbry.precise.api.schema.type

data class EnumType(
    override val name: String,
    val values: Set<String>
) : Type {
    override val type = Types.ENUM
}
