package io.datalbry.precise.validation.extensions

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.type.DocumentType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.Types

/**
 * Checks whether the [type] is a defined enum or not
 *
 * @return true if the [type] is defined as a enum, else false
 */
fun Schema.isDefinedEnumType(type: String) = this.types.firstOrNull { it.name == type }?.type == Types.ENUM

/**
 * Gets the [DocumentType] of the [type]
 *
 * @throws NoSuchElementException if the [type] has no corresponding [DocumentType] present in the [Schema]
 *
 * @return [DocumentType] of the [type]
 */
fun Schema.getDocumentType(type: String) = this.types.first { it.name == type } as DocumentType

/**
 * Gets the [EnumType] of the [type]
 *
 * @throws NoSuchElementException if the [type] has no corresponding [EnumType] present in the [Schema]
 *
 * @return [EnumType] of the [type]
 */
fun Schema.getEnumType(type: String) = this.types.first { it.name == type } as EnumType

