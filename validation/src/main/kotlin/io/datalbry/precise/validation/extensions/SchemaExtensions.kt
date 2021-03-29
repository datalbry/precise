package io.datalbry.precise.validation.extensions

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.Types

/**
 * Checks whether the [type] is a defined enum or not
 *
 * @return true if the [type] is defined as a enum, else false
 */
fun Schema.isDefinedEnumType(type: String) = this.types.firstOrNull { it.name == type }?.type == Types.ENUM

/**
 * Gets the [RecordType] of the [type]
 *
 * @throws NoSuchElementException if the [type] has no corresponding [RecordType] present in the [Schema]
 *
 * @return [RecordType] of the [type]
 */
fun Schema.getRecordType(type: String) = this.types.first { it.name == type } as RecordType

/**
 * Gets the [EnumType] of the [type]
 *
 * @throws NoSuchElementException if the [type] has no corresponding [EnumType] present in the [Schema]
 *
 * @return [EnumType] of the [type]
 */
fun Schema.getEnumType(type: String) = this.types.first { it.name == type } as EnumType

