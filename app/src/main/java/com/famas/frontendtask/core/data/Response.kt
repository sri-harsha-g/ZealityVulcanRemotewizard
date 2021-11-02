package com.famas.frontendtask.core.data

sealed class Response<T>(val source: T? = null, val message: String? = null) {
    class Success<T>(source: T?): Response<T>(source)
    class Error<T>(message: String?, source: T? = null): Response<T>(source, message)
}

