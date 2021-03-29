package io.datalbry.precise.api.schema.document.generic

import io.datalbry.precise.api.schema.document.Field

data class GenericField<T>(
    override val name: String,
    override val value: T
) : Field<T>
