package io.datalbry.precise.processor.kotlin

import com.tschuchort.compiletesting.SourceFile


val simpleItem = SourceFile.kotlin(
    "SimpleItem.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class SimpleItem(
        val name: String,
    )
    """
)
