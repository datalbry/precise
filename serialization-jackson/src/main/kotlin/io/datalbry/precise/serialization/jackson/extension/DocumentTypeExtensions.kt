package io.datalbry.precise.serialization.jackson.extension

import io.datalbry.precise.api.schema.type.RecordType

fun RecordType.getFieldSchema(field: String) = this.fields.first { it.name == field }
