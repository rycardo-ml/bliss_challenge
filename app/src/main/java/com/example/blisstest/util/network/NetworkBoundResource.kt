package com.example.blisstest.util.network

import com.example.blisstest.util.Resource
import kotlinx.coroutines.flow.*

//TODO Why emit blocks the process?

inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    //emit(Resource.Loading())
    val data = query().first()

    val flow = if (shouldFetch(data)) {
        //emit(Resource.Loading(data))

        try {
            saveFetchResult(fetch())
            query().map { Resource.Success(it, true) }
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            query().map { Resource.Error(throwable, it) }
        }
    } else {
        query().map { Resource.Success(it) }
    }

    emitAll(flow)
}