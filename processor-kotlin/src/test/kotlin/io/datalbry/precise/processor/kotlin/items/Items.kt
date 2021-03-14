package io.datalbry.precise.processor.kotlin.items

import com.tschuchort.compiletesting.SourceFile

val itemWithOnePrimitiveType = SourceFile.kotlin(
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

val itemWithMultiplePrimitiveTypes = SourceFile.kotlin(
    "ItemWithMultiplePrimitiveTypes.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class ItemWithMultiplePrimitiveTypes(
        val name: String,
        val count: Int,
        val hugeCount: Long,
        val currency: Float,
        val hugeCurrency: Double
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

val itemWithArrayType = SourceFile.kotlin(
    "ItemWithStringArray.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class ItemWithStringArray(
        val names: Array<String>
    )
    """
)

val itemWithPrimitiveArrayType = SourceFile.kotlin(
    "ItemWithPrimitiveArray.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class ItemWithPrimitiveArray(
        val numbers: IntArray
    )
    """
)

val itemWithOptionalType = SourceFile.kotlin(
    "ItemWithOptionalType.kt",
    """
    package io.datalbry.example

    import java.util.*
    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class ItemWithOptionalType(
        val name: Optional<String>
    )
    """
)

val itemWithNullableType = SourceFile.kotlin(
    "ItemWithNullableType.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class ItemWithNullableType(
        val name: String?
    )
    """
)

val itemWithCrossLinkToAuthor = SourceFile.kotlin(
    "ItemWithCrossLinkToAuthor.kt",
    """
    package io.datalbry.example

    import io.datalbry.example.Author
    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class ItemWithCrossLinkToAuthor(
        val name: String,
        val author: Author
    )
    """
)

val innerTypeAuthor = SourceFile.kotlin(
    "InnerTypeAuthor.kt",
    """
    package io.datalbry.example

    import io.datalbry.precise.api.schema.SchemaAware

    @SchemaAware
    data class Author(
        val name: String,
        val email: String
    )
    """
)
