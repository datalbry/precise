package io.datalbry.precise.processor.kotlin

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider

/**
 * [SchemaAwareProcessorProvider] is a concrete [SymbolProcessorProvider] providing access to
 * the [SchemaAwareProcessor]
 *
 * @author timo gruen - 2021-10-03
 */
class SchemaAwareProcessorProvider: SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
        return SchemaAwareProcessor(
            environment.codeGenerator,
            environment.logger
        )
    }
}
