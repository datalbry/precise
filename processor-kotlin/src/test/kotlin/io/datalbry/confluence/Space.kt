package io.datalbry.confluence

data class Space(
    val name: String = "Unknown",
    val description: String,
    val status: Status
)
