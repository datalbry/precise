package io.datalbry.precise.api.schema

import io.datalbry.precise.api.schema.type.Type

data class Schema(
    val namespace: String,
    val types: Set<Type>
)
