package io.datalbry.precise.processor.kotlin.items

import com.tschuchort.compiletesting.SourceFile


val itemWithOnePrimitiveType = SourceFile.kotlin(
    "Item.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class SimpleItem(
        val name: String,
    )
    """
)

val enumItem = SourceFile.kotlin(
    "Status.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    enum class Status {
        RUNNING,
        PAUSED,
        STOPPED
    }
    """
)

val itemWithMultiplePrimitiveTypes = SourceFile.kotlin(
    "Item.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class SimpleItem(
        val name: String,
        val count: Int,
        val hugeCount: Long,
        val currency: Float,
        val hugeCurrency: Double
    )
    """
)
