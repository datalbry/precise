package io.datalbry.precise.serialization.jackson.deserializer.assertion

import io.datalbry.precise.api.schema.document.Field
import org.junit.jupiter.api.Assertions

internal inline fun <reified T> assertValueType(field: Field<*>) {
    Assertions.assertTrue(field.value is T)
}

internal inline fun <reified T> assertContainsValues(field: Field<*>, vararg arguments: T) {
    val multiValue = field.value as Collection<T>
    arguments.forEach {
        Assertions.assertTrue(multiValue.contains(it)) {
            "Asserted Collection${multiValue.map { it.toString() }} contains $it"
        }
    }
}

internal inline fun <reified T> assertContainsValues(field: Field<*>, collection: Collection<T>) {
    val multiValue = field.value as Collection<T>
    collection.forEach {
        Assertions.assertTrue(multiValue.contains(it)) {
            "Asserted Collection${multiValue.map { it.toString() }} contains $it"
        }
    }
}
