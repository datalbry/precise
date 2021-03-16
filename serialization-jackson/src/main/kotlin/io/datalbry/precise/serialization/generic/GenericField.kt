package io.datalbry.precise.serialization.generic

import io.datalbry.precise.api.schema.document.Field

class GenericField<T>(
    override val name: String,
    override val value: T
) : Field<T>
