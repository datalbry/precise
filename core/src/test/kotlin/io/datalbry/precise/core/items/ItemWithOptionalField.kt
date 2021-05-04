package io.datalbry.precise.core.items

import io.datalbry.precise.api.schema.SchemaAware
import java.util.*

@SchemaAware
data class ItemWithOptionalField(
    val title: String,
    val optionalBoolean: Optional<Boolean>,
    val optionalString: Optional<String>,
)
