package com.barret73.springboot.accounts.api

import com.barret73.springboot.accounts.config.logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

// TODO should be moved to a shared module
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, Any>> {
        val errors =
            ex.bindingResult.fieldErrors.associate {
                it.field to (it.defaultMessage ?: "Invalid value")
            }
        val responseBody = mapOf("error" to "Validation failed", "details" to errors)
        return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<Map<String, Any>> {
        val rawMessage =
            ex.cause?.localizedMessage ?: ex.localizedMessage ?: "Malformed JSON request"
        val missingFieldRegex = Regex("""JSON property (\w+) due to missing""")
        val match = missingFieldRegex.find(rawMessage)
        val userMessage =
            if (match != null) {
                val field = match.groupValues[1]
                "The '$field' field is required and must not be null or missing in the request body."
            } else {
                "Malformed JSON request. Please check your input."
            }
        val responseBody = mapOf("error" to "Malformed request", "details" to userMessage)
        return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<Map<String, Any>> {
        val responseBody =
            mapOf(
                "error" to "Internal server error",
                "details" to (ex.localizedMessage ?: "Unexpected error occurred"),
            )
        logger.error(ex) { "Error while processing a request" }
        return ResponseEntity(responseBody, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
