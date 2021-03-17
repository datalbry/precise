package io.datalbry.precise.serialization.jackson.document.assertion

import io.datalbry.precise.api.schema.document.Field
import org.junit.jupiter.api.Assertions

inline fun <reified T> assertValueType(field: Field<*>) {
    Assertions.assertTrue(field.value is T)
}
