package io.datalbry.precise.serialization.generic

import io.datalbry.precise.api.schema.document.Field

class GenericField(
    override val name: String,
    override val value: Any
) : Field<Any> {

}
