package io.datalbry.precise.serialization.jackson.extension

import com.fasterxml.jackson.databind.JsonNode

fun <T> JsonNode.mapValues(field: String, transform: (JsonNode) -> T ): Set<T> =
    this.get(field).elements().asSequence().map(transform).toSet()

fun JsonNode.asFloat(): Float {
    return this.asDouble().toFloat()
}
