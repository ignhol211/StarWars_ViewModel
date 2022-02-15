package com.example.ejemplointernet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException

class SecondActivityViewModel  : ViewModel() {

    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible : LiveData<Boolean>
        get() = _isVisible

    private val _responsePlaneta by lazy { MediatorLiveData<Planeta>() }
    val responsePlaneta : LiveData<Planeta>
        get() = _responsePlaneta

    suspend fun setIsVisibleInMainThread(value : Boolean) = withContext(Dispatchers.Main){
        _isVisible.value = value
    }

    suspend fun setResponseTextInMainThread(planeta: Planeta) = withContext(Dispatchers.Main){
        _responsePlaneta.value = planeta
    }

    fun getPlanet(initialData: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)


                val client = OkHttpClient()

                val request = Request.Builder()
                request.url(initialData)

                val call = client.newCall(request.build())
                call.enqueue( object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setIsVisibleInMainThread(false)
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()

                            val planeta = gson.fromJson(body, Planeta::class.java)

                            CoroutineScope(Dispatchers.Main).launch {
                                setResponseTextInMainThread(planeta)
                            }
                        }
                    }
                })
            }
        }
    }

}