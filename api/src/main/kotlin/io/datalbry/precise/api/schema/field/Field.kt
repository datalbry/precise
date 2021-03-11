package io.datalbry.precise.api.schema.field

import java.util.*

data class Field(
    val name: String,
    val type: String,
    val default: Optional<String> = Optional.empty(),
    val multiValue: Boolean = false,
    val optional: Boolean = false
)
