package io.datalbry.precise.serialization.jackson.deserializer.assertion

import io.datalbry.precise.api.schema.document.Document
import io.datalbry.precise.api.schema.document.Field
import io.datalbry.precise.api.schema.document.Record
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue

internal inline fun <reified T> assertValueType(field: Field<*>) {
    Assertions.assertTrue(field.value is T)
}

@SuppressWarnings("unchecked")
internal inline fun <reified T> assertContainsValues(field: Field<*>, vararg arguments: T) {
    val multiValue = field.value as Collection<T>
    arguments.forEach {
        Assertions.assertTrue(multiValue.contains(it)) {
            "Asserted Collection${multiValue.map { it.toString() }} contains $it"
        }
    }
}

@SuppressWarnings("unchecked")
internal inline fun <reified T> assertContainsValues(field: Field<*>, collection: Collection<T>) {
    val multiValue = field.value as Collection<T>
    collection.forEach {
        Assertions.assertTrue(multiValue.contains(it)) {
            "Asserted Collection${multiValue.map { it.toString() }} contains $it"
        }
    }
}

internal fun assertFieldPresent(s: String, record: Record) {
    assertTrue(record.getKeys().contains(s)) { "Record of type[${record.type}] does not contain key[$s]" }
}
