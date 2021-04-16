package io.datalbry.precise.core.util

import io.datalbry.precise.api.schema.field.BasicFieldType
import java.util.*

private val fieldTypeToClassesMap = mapOf(
    BasicFieldType.BOOLEAN to arrayOf(Boolean::class),
    BasicFieldType.BYTE to arrayOf(Byte::class, UByte::class),
    BasicFieldType.FLOAT to arrayOf(Float::class),
    BasicFieldType.DOUBLE to arrayOf(Double::class),
    BasicFieldType.INT to arrayOf(Short::class, Int::class),
    BasicFieldType.LONG to arrayOf(Long::class),
    BasicFieldType.STRING to arrayOf(String::class, CharSequence::class, Array<Char>::class)
)

fun isBasicFieldType(clazz: Class<*>): Boolean = getBasicFieldTypeOrNull(clazz) != null
fun getBasicFieldType(clazz: Class<*>): BasicFieldType = getBasicFieldTypeOrNull(clazz)!!
fun getBasicFieldTypeOrNull(clazz: Class<*>): BasicFieldType? =
    fieldTypeToClassesMap.entries.firstOrNull { it.value.any { v -> v.java.isAssignableFrom(clazz) } }?.key

fun isBasicFieldType(obj: Any?): Boolean = getBasicFieldTypeOrNull(obj) != null
fun getBasicFieldType(obj: Any?): BasicFieldType = getBasicFieldTypeOrNull(obj)!!
fun getBasicFieldTypeOrNull(obj: Any?): BasicFieldType? =
    fieldTypeToClassesMap.entries.firstOrNull { it.value.any { v -> v.isInstance(obj) } }?.key
