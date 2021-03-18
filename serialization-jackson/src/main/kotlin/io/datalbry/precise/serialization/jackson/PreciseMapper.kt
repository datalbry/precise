package io.datalbry.precise.serialization.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import io.datalbry.precise.api.schema.Schema

/**
 * Precise specific [ObjectMapper] with preconfigured options
 *
 * Precise is statically typed and requires a [Schema] for serialization*
 *
 * @param schema for precise to use
 */
class PreciseMapper(schema: Schema): ObjectMapper() {

    init {
        val preciseModule = PreciseModule(schema)
        this.registerModule(preciseModule)
    }

}
