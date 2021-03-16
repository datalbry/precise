package io.datalbry.precise.serialization.jackson.extension

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.type.DocumentType
import io.datalbry.precise.api.schema.type.Types

fun Schema.isDefinedDocumentType(type: String) = this.types.firstOrNull { it.name == type }?.type == Types.DOCUMENT

fun Schema.isDefinedEnumType(type: String) = this.types.firstOrNull { it.name == type }?.type == Types.ENUM

fun Schema.getDocumentType(type: String) = this.types.first { it.name == type } as DocumentType

fun Schema.definesType(type: String) = this.types.any { it.name == type }

fun Schema.typeSchema(type: String) = this.types.first { it.name == type }
