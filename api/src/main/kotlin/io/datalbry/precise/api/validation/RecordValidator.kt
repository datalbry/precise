package io.datalbry.precise.api.validation

import io.datalbry.precise.api.schema.Schema
import io.datalbry.precise.api.schema.document.Record

/**
 * [RecordValidator] validates [Record] using their [Schema] definition
 *
 * @author timo gruen - 2021-03-17
 */
interface RecordValidator: Validator<Record>
