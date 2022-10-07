package com.appsoft.splyza.data.network

sealed class ResourceState<T> {
    data class Success<T>(val body: T, val code: Int = 200) : ResourceState<T>()

    data class Failure<T>(
        val throwable: Throwable,
        val code: String = "",
        val body: T? = null
    ) : ResourceState<T>()
}