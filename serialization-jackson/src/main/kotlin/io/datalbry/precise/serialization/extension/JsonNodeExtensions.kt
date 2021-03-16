package io.datalbry.precise.serialization.extension

import com.fasterxml.jackson.databind.JsonNode

fun <T> JsonNode.mapValues(field: String, transform: (JsonNode) -> T ): Set<T> =
    this.get(field).elements().asSequence().map(transform).toSet()
