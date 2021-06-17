package io.datalbry.precise.validation

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record
import io.datalbry.precise.api.schema.type.EnumType
import io.datalbry.precise.api.schema.type.RecordType
import io.datalbry.precise.api.serialization.DocumentDeserializer
import io.datalbry.precise.api.validation.DocumentValidator
import io.datalbry.precise.api.validation.ValidationError
import io.datalbry.precise.api.validation.ValidationResult
import io.datalbry.precise.validation.extensions.*
import io.datalbry.precise.validation.util.*
import java.io.File
import io.datalbry.precise.api.schema.field.Field as FieldSchema

/**
 * Standard implementation for [DocumentValidator]
 *
 * @author timo gruen - 2021-03-17
 */
class DocumentValidatorImpl(private val deserializer: DocumentDeserializer) : DocumentValidator {

    override fun isValid(schema: Schema, type: Document): Boolean {
        return validate(schema, type).isValid
    }

    override fun isValid(schema: Schema, file: File): Boolean {
        return try {
            val document = deserializer.read(schema, file)
            isValid(schema, document)
        } catch (e: Exception) {
            false
        }
    }

    override fun validate(schema: Schema, type: Document): ValidationResult {
        val error = validateRecord(schema, schema.getRecordType(type.type), type)
        return if (error == null) {
            ValidationResult(
                isValid = true,
                error = null
            )
        } else {
            ValidationResult(
                isValid = false,
                error = error
            )
        }
    }

    private fun validateRecord(
        schema: Schema,
        typeDefinition: RecordType,
        record: Record
    ): ValidationError.Value.Record? {
        val errors = typeDefinition.fields.mapNotNull { expected ->
            val actualSchema = record.fields.firstOrNull { actual ->
                actual.name == expected.name
            }
            if (actualSchema == null) {
                ValidationError.Field(
                    fieldName = expected.name,
                    valueErrors = listOf(ValidationError.Value.MissingValue(
                        expectedType = expected.type
                    ))
                )
            } else {
                validateField(expected, actualSchema, schema)
            }
        }
        return if (errors.isEmpty()) {
            null
        } else {
            ValidationError.Value.Record(
                recordType = typeDefinition.name,
                fieldErrors = errors
            )
        }
    }

    private fun validateField(
        fieldSchema: io.datalbry.precise.api.schema.field.Field,
        it: Field<*>,
        schema: Schema
    ): ValidationError.Field? {
        val value = it.value
        return when {
            fieldSchema.multiValue || fieldSchema.optional -> {
                val errors = validateMultiValue(value, fieldSchema, schema, fieldSchema.optional)
                if (errors.isEmpty()) {
                    null
                } else {
                    ValidationError.Field(fieldSchema.name, errors)
                }
            }
            else -> ValidationError.Field(
                fieldSchema.name,
                listOf(validateValue(fieldSchema, value, schema) ?: return null)
            )
        }
    }

    private fun validateMultiValue(
        value: Any?,
        fieldSchema: FieldSchema,
        schema: Schema,
        optional: Boolean
    ): List<ValidationError.Value> {
        return if (value == null) {
            if (optional) {
                emptyList()
            } else {
                listOf(ValidationError.Value.MissingValue(fieldSchema.type))
            }
        } else if (value !is Iterable<*>) {
            listOf(ValidationError.Value.WrongType(
                expectedType = fieldSchema.type,
                actualType = basicFieldTypeByValue(value)?.id
            ))
        } else {
            value.mapNotNull { validateValue(fieldSchema, it, schema) }
        }
    }

    private fun validateValue(fieldSchema: FieldSchema, value: Any?, schema: Schema): ValidationError.Value? {
        val type = fieldSchema.type
        return when {
            isBasicFieldType(type) -> validateBasicValue(fieldSchema, value)
            schema.isDefinedEnumType(type) -> validateEnum(schema.getEnumType(type), value)
            value is Record -> validateRecord(schema, schema.getRecordType(type), value)
            else -> ValidationError.Value.WrongType(
                expectedType = type,
                actualType = basicFieldTypeByValue(value)?.id
            )
        }
    }

    private fun validateEnum(enum: EnumType, value: Any?): ValidationError.Value? {
        return when {
            value !is String -> null
            enum.values.contains(value) -> null
            else -> ValidationError.Value.UnknownEnumConstant(
                expectedType = enum.name,
                allowedValues = enum.values,
                actualValue = value
            )
        }
    }

    private fun validateBasicValue(
        field: io.datalbry.precise.api.schema.field.Field,
        value: Any?
    ): ValidationError.Value? {
        val expected = basicFieldTypeById(field.type)
        val actual = basicFieldTypeByValue(value)
        return if (expected != actual) {
            ValidationError.Value.WrongType(
                expectedType = expected.id,
                actualType = actual?.id
            )
        } else {
            null
        }
    }
}
