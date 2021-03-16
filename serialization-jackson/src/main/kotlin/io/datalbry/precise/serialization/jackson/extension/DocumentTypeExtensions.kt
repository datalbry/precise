package io.datalbry.precise.serialization.jackson.extension

import io.datalbry.precise.api.schema.type.DocumentType

fun DocumentType.getFieldSchema(field: String) = this.fields.first { it.name == field }
