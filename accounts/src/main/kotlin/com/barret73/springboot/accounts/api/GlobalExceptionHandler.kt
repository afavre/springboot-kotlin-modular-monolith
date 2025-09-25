package com.barret73.springboot.accounts.api

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

// TODO should be moved to a shared module
@ControllerAdvice
class GlobalExceptionHandler {
    val logger = KotlinLogging.logger {}

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors =
            ex.bindingResult.fieldErrors.associate {
                it.field to (it.defaultMessage ?: "Invalid value")
            }
        val responseBody = mapOf("error" to "Validation failed", "details" to errors)
        return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(ex: HttpMessageNotReadableException): ResponseEntity<Map<String, Any>> {
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

    @ExceptionHandler(NoResourceFoundException::class, NoHandlerFoundException::class)
    fun handleNoResourceFound(ex: NoResourceFoundException): ResponseEntity<String> = ResponseEntity.notFound().build()

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
