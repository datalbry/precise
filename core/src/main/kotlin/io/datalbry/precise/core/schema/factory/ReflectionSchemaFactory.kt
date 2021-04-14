package io.datalbry.precise.core.schema.factory

import io.datalbry.precise.api.schema.Exclude
import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.factory.SchemaFactory
import io.datalbry.precise.api.schema.field.BasicFieldType
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.schema.type.Type
import io.datalbry.precise.core.util.getBasicFieldType
import io.datalbry.precise.core.util.isBasicFieldType
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.util.*
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.jvm.javaField
import io.datalbry.precise.api.schema.field.Field as FieldSchema

/**
 * [ReflectionSchemaFactory] is a [SchemaFactory] which is able to derive a [Schema] using information from [Class]es
 *
 * Heavily relying on reflection to get the internal property definitions,
 * as well as fetching annotation information.
 *
 * @author timo gruen - 2021-04-14
 */
class ReflectionSchemaFactory: SchemaFactory<Class<*>> {

    private val toBeProcessed = mutableSetOf<Class<*>>()
    private val alreadyProcessed = mutableSetOf<Class<*>>()

    override fun deriveSchema(vararg from: Class<*>): Schema {
        synchronized(this) {
            toBeProcessed.addAll(from)
            val types = mutableSetOf<Type>()
            while (toBeProcessed.isNotEmpty()) {
                types.add(deriveType(toBeProcessed.first()))
                toBeProcessed.removeAll(alreadyProcessed)
            }
            return Schema(types.toSet())
        }
    }

    private fun deriveType(clazz: Class<*>): Type {
        toBeProcessed.remove(clazz)
        alreadyProcessed.add(clazz)
        return when {
            clazz.isEnum -> deriveEnumType(clazz)
            else -> deriveRecordType(clazz)
        }
    }

    private fun deriveRecordType(clazz: Class<*>): RecordType {
        val fields = clazz.kotlin.declaredMemberProperties
            .filterNot { it.hasAnnotation<Exclude>() }
            .mapNotNull { it.javaField }
            .map { it.toFieldSchema() }
        return RecordType(clazz.simpleName, fields.toSet())
    }

    private fun deriveEnumType(clazz: Class<*>): EnumType {
        assert(clazz.isEnum)
        val name = clazz.simpleName
        val values = clazz.enumConstants.filterIsInstance<Enum<*>>().map { it.name }.toSet()
        return EnumType(name, values)
    }

    private fun Field.toFieldSchema(): FieldSchema {
        return FieldSchema(name, getFieldTypeRecursively(), isMultiValued(), isOptional())
    }

    private fun Field.getFieldTypeRecursively(): String {
        return when {
            isBasicFieldType(type) -> getBasicFieldType(type).id
            isArray(type) -> typeNameOfArray()
            isOptional(type) -> typeNameOfGeneric()
            isCollection(type) -> typeNameOfGeneric()
            else ->  { type.simpleName.also { toBeProcessed.add(type) } }
        }
    }

    private fun Field.typeNameOfArray(): String {
        return genericType.typeName
    }

    private fun Field.typeNameOfGeneric(): String {
        val type = (this.genericType as ParameterizedType).actualTypeArguments[0]
        val typeString = type.typeName.substringAfterLast(".")
        if (isBasicTypeId(typeString)) return typeString.toLowerCase()
        return typeString
    }

    private fun isBasicTypeId(typeString: String) = BasicFieldType.values().map {
        it.id.toLowerCase()
    }.contains(typeString)

    private fun Field.isOptional() = isOptional(this.type) || annotatedWithNullable()

    private fun Field.annotatedWithNullable() = this.declaredAnnotations.any {
        val name = it.annotationClass.simpleName?.toLowerCase() ?: ""
        return setOf("nullable", "null").contains(name)
    }

    private fun Field.isMultiValued(): Boolean = isArray(type) || isCollection(type)
}

private fun isArray(type: Class<*>): Boolean = type.isArray

private fun isOptional(type: Class<*>): Boolean = Optional::class.java.isAssignableFrom(type)

private fun isCollection(type: Class<*>): Boolean = Collection::class.java.isAssignableFrom(type)


