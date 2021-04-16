package io.datalbry.precise.core.items

import io.datalbry.precise.api.schema.SchemaAware

@SchemaAware
data class ItemWithCollectionField(
    val name: String,
    val author: Person,
    val contributors: Collection<Person>
)
