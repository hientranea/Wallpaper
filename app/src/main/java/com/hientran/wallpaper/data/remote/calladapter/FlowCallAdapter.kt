package com.hientran.wallpaper.data.remote.calladapter

import com.hientran.wallpaper.data.mapper.mapperToAppException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FlowCallAdapter<T>(private val responseType: Type): CallAdapter<T, Flow<T>> {
    override fun responseType() = responseType

    override fun adapt(call: Call<T>): Flow<T> = flow {
        emit(suspendCancellableCoroutine { cancellableContinuation ->
            call.enqueue(object: Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    try {
                        cancellableContinuation.resume(response.body()!!)
                    } catch (e: Exception) {
                        cancellableContinuation.resumeWithException(
                            e.mapperToAppException()
                        )
                    }
                }

                override fun onFailure(call: Call<T>, t: Throwable) {
                    cancellableContinuation.resumeWithException(t.mapperToAppException())
                }
            })

            cancellableContinuation.invokeOnCancellation { call.cancel() }
        })
    }
}
