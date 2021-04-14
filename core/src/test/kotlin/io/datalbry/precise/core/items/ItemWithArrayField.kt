package io.datalbry.precise.core.items

import io.datalbry.precise.api.schema.SchemaAware

@SchemaAware
data class ItemWithArrayField(
    val name: String,
    val author: Person,
    val contributors: Array<Person>
)
