package com.example.blisstest.util.network

import com.example.blisstest.util.Resource
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    val storeData = query().first()

    if (!shouldFetch(storeData)) {
        emitAll(query().map { Resource.Success(it) })
        return@flow
    }

    emit(Resource.Loading(storeData))
    emitAll(
        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it) }
        } catch (throwable: Throwable){
            query().map { Resource.Error(throwable, it) }
        }
    )
}