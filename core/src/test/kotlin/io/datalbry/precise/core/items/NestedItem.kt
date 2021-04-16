package io.datalbry.precise.core.items

import io.datalbry.precise.api.schema.SchemaAware

@SchemaAware
data class NestedItem(
    val name: String,
    val author: Person
)

