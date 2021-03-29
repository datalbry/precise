package io.datalbry.precise.validation

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Record

import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.serialization.DocumentDeserializer
import io.datalbry.precise.api.validation.DocumentValidator
import io.datalbry.precise.validation.extensions.*
import io.datalbry.precise.validation.util.*
import java.io.File

import io.datalbry.precise.api.schema.field.Field as FieldSchema
import io.datalbry.precise.api.schema.document.Field as Field

/**
 * Standard implementation for [DocumentValidator]
 *
 * @author timo gruen - 2021-03-17
 */
class DocumentValidatorImpl(private val deserializer: DocumentDeserializer): DocumentValidator {

    override fun isValid(schema: Schema, type: Document): Boolean {
        return validateRecord(schema, schema.getRecordType(type.type), type)
    }

    override fun isValid(schema: Schema, file: File): Boolean {
        return try {
            val document = deserializer.read(schema, file)
            isValid(schema, document)
        } catch (e: Exception) { false }
    }


    private fun validateRecord(schema: Schema, typeDefinition: RecordType, record: Record): Boolean {
        val validFieldTypes = record.fields.map { validateField(typeDefinition, it, schema) }.all { it }
        val noMissingFields = noMissingFields(typeDefinition, record)
        return validFieldTypes && noMissingFields
    }


    private fun noMissingFields(typeDefinition: RecordType, record: Record): Boolean {
        val presentFields = record.fields.map(Field<*>::name)
        val mandatoryFields = typeDefinition.fields.filterNot { it.optional }.map { it.name }
        return presentFields.containsAll(mandatoryFields)
    }

    private fun validateField(typeSchema: RecordType, it: Field<*>, schema: Schema): Boolean {
        val fieldSchema = typeSchema.getFieldType(it.name)
        val value = it.value
        return when {
            fieldSchema.multiValue -> validateMultiValue(value, fieldSchema, schema)
            fieldSchema.optional -> value is Collection<*> && value.all { validateValue(fieldSchema, value, schema) }
            else -> validateValue(fieldSchema, value, schema)
        }
    }

    private fun validateMultiValue(value: Any?, fieldSchema: FieldSchema, schema: Schema): Boolean {
        return when (value) {
            is Collection<*> -> value.all { validateValue(fieldSchema, it, schema) }
            is Array<*> -> value.all { validateValue(fieldSchema, it, schema) }
            else -> false
        }
    }

    private fun validateValue(fieldSchema: FieldSchema, value: Any?, schema: Schema): Boolean {
        val type = fieldSchema.type
        return when {
            isBasicFieldType(type) -> isValidBasicType(fieldSchema, value)
            schema.isDefinedEnumType(type) -> isValidEnum(schema.getEnumType(type), value)
            value is Record -> validateRecord(schema, schema.getRecordType(type), value)
            else -> false
        }
    }

}
