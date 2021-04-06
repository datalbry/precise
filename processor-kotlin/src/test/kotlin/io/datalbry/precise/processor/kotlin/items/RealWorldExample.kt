package io.datalbry.precise.processor.kotlin.items

import com.tschuchort.compiletesting.SourceFile

val book = SourceFile.kotlin(
    "Book.kt",
    """
    package io.datalbry.example
    
    import java.util.*
    import io.datalbry.precise.api.schema.SchemaAware
    import io.datalbry.example.Person

    @SchemaAware
    data class Book(
        val title: String,
        val author: Person,
        val contributors: Set<Person>,
        val released: Optional<String>
    )

    @SchemaAware
    data class Person(
        val name: String,
        val email: String
    )
    """
)

val space = SourceFile.kotlin(
    "Space.kt",
    """
    package io.datalbry.example
    
    import java.util.*
    import io.datalbry.precise.api.schema.SchemaAware
    import io.datalbry.example.Person

    @SchemaAware
    data class Space(
        @Id val id: Long,
        val title: String,
        val author: Person,
        val contributors: Set<Person>,
        val created: Optional<String>,
        val last_updated: Optional<String>,
        @Children @Ignored val pageRefs: Collection<PageRef>
    )

    @SchemaAware
    data class Person(
        val name: String,
        val email: String
    )

    @SchemaAware 
    data class Page(
        @Id val spaceId: Long,
        @Id val pageId: Long
    )

    class RootProcessor<RootRef, ?> {
    
        fun convert(ref: PageRef): Collection<Page> {
             return setOf(confluenceClient.getPage(ref.spaceId, ref.pageId))
        }
    }

    class PageProcessor<PageRef, Page> {
    
        fun convert(ref: PageRef): Collection<Page> {
             return setOf(confluenceClient.getPage(ref.spaceId, ref.pageId))
        }
    }
    """
)
