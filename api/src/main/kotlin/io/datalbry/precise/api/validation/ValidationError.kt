package io.datalbry.precise.api.validation

sealed class ValidationError(
    val errorType: ValidationErrorType
) {
    sealed class Value(
        val valueErrorType: ValueErrorType
    ) : ValidationError(
        errorType = ValidationErrorType.VALUE
    ) {
        data class MissingValue(
            val expectedType: String
        ) : Value(valueErrorType = ValueErrorType.MISSING)
        data class WrongType(
            val expectedType: String,
            val actualType: String?
        ) : Value(valueErrorType = ValueErrorType.WRONG_TYPE)
        data class UnknownEnumConstant(
            val expectedType: String,
            val allowedValues: Set<String>,
            val actualValue: String
        ) : Value(valueErrorType = ValueErrorType.UNKNOWN_ENUM_CONSTANT)
        data class Record(
            val recordType: String,
            val fieldErrors: List<Field>
        ) : Value(valueErrorType = ValueErrorType.RECORD)
    }

    data class Field(
        val fieldName: String,
        val valueErrors: List<Value>
    ) : ValidationError(errorType = ValidationErrorType.FIELD)
}
