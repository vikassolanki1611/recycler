package com.recyclerapplication.webservice

import android.util.Log
import com.google.gson.JsonSyntaxException
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException

abstract class NetworkCall {
    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): NetworkState<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                NetworkState.Success(response.body()!!,response.headers().values("Set-Cookie"))
            } else {
                Log.e("yyyyyyy","${response}")
                when (response.code()) {

                    403 -> NetworkState.HttpErrors.ResourceForbidden(response.message())
                    404 -> NetworkState.HttpErrors.ResourceNotFound(response.message())
                  406, 409-> NetworkState.HttpErrors.ErrorEncountered(response.code().toString())
                    500 -> NetworkState.HttpErrors.InternalServerError(response.message())
                    502 -> NetworkState.HttpErrors.BadGateWay(response.message())
                    301 -> NetworkState.HttpErrors.ResourceRemoved(response.message())
                    302 -> NetworkState.HttpErrors.RemovedResourceFound(response.message())
                    else -> NetworkState.Error(response.body())
                }
            }

        }
        catch (error: JsonSyntaxException) {
            error.printStackTrace()
            NetworkState.NetworkException("Server Model Issue on ${error.localizedMessage}")
           // error.printStackTrace()
        }catch (error: IOException) {
            error.printStackTrace()
            NetworkState.NetworkException("Server Model Issue on ${error.localizedMessage}")
        }
    }



    private fun getThis(message: ResponseBody?): Any {
        val jsonObj = JSONObject(message!!.charStream().readText())
        Log.e("ERRRRROOORRR",jsonObj.getString("message"))
        return jsonObj
    }
}