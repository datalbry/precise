package io.datalbry.precise.api.serialization

import io.datalbry.precise.api.schema.document.Document

/**
 * [DocumentDeserializer] is a basic interface for [Document] serialization
 *
 * It might only act as a entry point if one is not basically using a standard JSON framework such as Jackson,
 * Gson and others.
 *
 * @author timo gruen - 2021-03-17
 */
interface DocumentDeserializer: Deserializer<Document>
