package io.datalbry.precise.core.items

import io.datalbry.precise.api.schema.Exclude
import io.datalbry.precise.api.schema.SchemaAware

@SchemaAware
data class ItemWithExcludedField(
    val title: String,
    @Exclude val id: Int
)
