package io.datalbry.precise.serialization.jackson.extension

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.schema.type.Types

fun Schema.isRecordType(type: String) = this.types.firstOrNull { it.name == type }?.type == Types.RECORD

fun Schema.isEnumType(type: String) = this.types.firstOrNull { it.name == type }?.type == Types.ENUM

fun Schema.getRecordType(type: String) = this.types.first { it.name == type } as RecordType

fun Schema.getEnumType(type: String) = this.types.first { it.name == type } as EnumType

fun Schema.definesType(type: String) = this.types.any { it.name == type }

fun Schema.typeSchema(type: String) = this.types.first { it.name == type }
