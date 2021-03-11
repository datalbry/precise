package io.datalbry.precise.processor.kotlin.items

import com.tschuchort.compiletesting.SourceFile

fun getSimpleItem() = SourceFile.kotlin(
    "SimpleItem.kt",
    """
        package io.datalbry.test.schema

        import io.datalbry.precise.api.schema.SchemaAware

        @SchemaAware
        data class SimpleItem(
            val key: String,
            val title: String,
        )
        """
)
