package io.datalbry.precise.serialization.jackson.util

import io.datalbry.precise.api.schema.field.BasicFieldType
import java.util.*

fun isBasicFieldType(obj: Any?): Boolean {
    return getBasicFieldTypeOrNull(obj) != null
}

private val fieldTypeToClassesMap = mapOf(
    BasicFieldType.BOOLEAN to arrayOf(Boolean::class),
    BasicFieldType.BYTE to arrayOf(Byte::class, UByte::class),
    BasicFieldType.FLOAT to arrayOf(Float::class),
    BasicFieldType.DOUBLE to arrayOf(Double::class),
    BasicFieldType.INT to arrayOf(Short::class, Int::class),
    BasicFieldType.LONG to arrayOf(Long::class),
    BasicFieldType.STRING to arrayOf(String::class, CharSequence::class, Array<Char>::class)
)

fun getBasicFieldType(obj: Any?): BasicFieldType {
    return fieldTypeToClassesMap.entries.firstOrNull { it.value.any { v -> v.isInstance(obj) } }!!.key
}

fun getBasicFieldTypeOrNull(obj: Any?): BasicFieldType? {
    return fieldTypeToClassesMap.entries.firstOrNull { it.value.any { v -> v.isInstance(obj) } }?.key
}
