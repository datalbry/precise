package io.datalbry.precise.core.items

import io.datalbry.precise.api.schema.SchemaAware

@SchemaAware
data class ItemWithNullableField(
    val name: String?
)
