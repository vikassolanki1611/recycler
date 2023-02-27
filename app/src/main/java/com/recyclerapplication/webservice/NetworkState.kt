package com.recyclerapplication.webservice


sealed class NetworkState<out T : Any> {
//    abstract fun headers(): Any,
    data class Success<T : Any>(val body: T, val cookie: List<String>) : NetworkState<T>()

    object InvalidData : NetworkState<Nothing>()
    data class Error<T : Any>(val msg: T?) : NetworkState<T>()
    data class NetworkException(val msg: String) : NetworkState<Nothing>()
    sealed class HttpErrors : NetworkState<Nothing>() {
        data class ResourceForbidden(val msg: String) : HttpErrors()
        data class ResourceNotFound(val msg: String) : HttpErrors()
        data class InternalServerError(val msg: String) : HttpErrors()
        data class BadGateWay(val msg: String) : HttpErrors()
        data class ErrorEncountered(val body: String) : HttpErrors()
        data class ResourceRemoved(val msg: String) : HttpErrors()
        data class RemovedResourceFound(val msg: String) : HttpErrors()
    }

    // Here msg is exception and error
}