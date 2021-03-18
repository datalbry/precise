package io.datalbry.precise.validation.extensions

import io.datalbry.precise.api.schema.field.BasicFieldType

/**
 * Searches for the [BasicFieldType] by the given [id]
 *
 * @throws NoSuchElementException if there is no [BasicFieldType] with the provided [id]
 *
 * @return corresponding [BasicFieldType] of the [id]
 */
fun basicFieldTypeById(id: String) = BasicFieldType.values().first { it.id == id }

/**
 * Searches for the [BasicFieldType] by the given [id]
 *
 * @return true if there is a [BasicFieldType] with the given [id], else false
 */
fun isBasicFieldType(id: String) = BasicFieldType.values().any { it.id == id }
