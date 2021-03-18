package io.datalbry.precise.validation

import io.datalbry.precise.serialization.jackson.JacksonDocumentDeserializer
import io.datalbry.precise.validation.util.getTestDocument
import io.datalbry.precise.validation.util.getTestSchema
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class DocumentValidatorImplTest {

    private val deserializer = JacksonDocumentDeserializer()
    private val validator = DocumentValidatorImpl(deserializer)

    @Test
    fun isValid_documentWithNestedRecord_isValid() {
        val schema = getTestSchema<DocumentValidatorImplTest>("Employee.json")
        val json = getTestDocument<DocumentValidatorImplTest>("Employee.json")
        assertTrue(validator.isValid(schema, json))
    }

    @Test
    fun isValid_documentAndSchemaMissMatch_isNotValid() {
        val schema = getTestSchema<DocumentValidatorImplTest>("Book.json")
        val json = getTestDocument<DocumentValidatorImplTest>("InvalidBook.json")
        assertFalse(validator.isValid(schema, json))
    }

    @Test
    fun isValid_documentWithMissingMandatoryField_isNotValid() {
        val schema = getTestSchema<DocumentValidatorImplTest>("Company.json")
        val json = getTestDocument<DocumentValidatorImplTest>("CompanyWithoutName.json")
        assertFalse(validator.isValid(schema, json))
    }

    @Test
    fun isValid_documentWithMultiValuedNestedRecord_isValid() {
        val schema = getTestSchema<DocumentValidatorImplTest>("Book.json")
        val json = getTestDocument<DocumentValidatorImplTest>("Book.json")
        assertTrue(validator.isValid(schema, json))
    }

    @Test
    fun isValid_documentWithMissingOptionalField_isValid() {
        val schema = getTestSchema<DocumentValidatorImplTest>("Document.json")
        val json = getTestDocument<DocumentValidatorImplTest>("DocumentWithUnknownFields.json")
        assertTrue(validator.isValid(schema, json))
    }

}
