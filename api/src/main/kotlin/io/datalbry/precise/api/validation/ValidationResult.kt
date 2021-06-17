package io.datalbry.precise.api.validation

data class ValidationResult(
    val isValid: Boolean,
    val error: ValidationError.Value.Record?
)

