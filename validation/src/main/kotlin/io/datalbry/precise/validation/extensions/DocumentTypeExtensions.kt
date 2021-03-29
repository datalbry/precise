package io.datalbry.precise.validation.extensions

import io.datalbry.precise.api.schema.type.RecordType

/**
 * Gets the [io.datalbry.precise.api.schema.field.Field] of the corresponding [field]
 *
 * @throws NoSuchElementException if there is no [io.datalbry.precise.api.schema.field.Field] with the given [field] key
 *
 * @return [io.datalbry.precise.api.schema.field.Field] containing the schema definition of the field
 */
fun RecordType.getFieldType(field: String) = this.fields.first { it.name == field }

