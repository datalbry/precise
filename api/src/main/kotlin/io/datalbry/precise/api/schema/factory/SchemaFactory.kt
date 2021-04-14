package io.datalbry.precise.api.schema.factory

import io.datalbry.precise.api.schema.Schema

interface SchemaFactory<From> {

    fun deriveSchema(vararg from: From): Schema

}
