package com.example.ejemplointernet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import kotlinx.coroutines.*
import okhttp3.*
import java.io.IOException
class MainActivityViewModel : ViewModel() {

    private val _isVisible by lazy { MediatorLiveData<Boolean>() }
    val isVisible : LiveData<Boolean>
        get() = _isVisible

    private val _responsePlaneta by lazy { MediatorLiveData<PlanetaResponse>() }
    val responsePlaneta : LiveData<PlanetaResponse>
        get() = _responsePlaneta

    private val _responseText by lazy { MediatorLiveData<String>() }
    val responseText : LiveData<String>
        get() = _responseText

    suspend fun setIsVisibleInMainThread(value : Boolean) = withContext(Dispatchers.Main){
        _isVisible.value = value
    }

    suspend fun setResponsePlanetaInMainThread(planetaResponse: PlanetaResponse) = withContext(Dispatchers.Main){
        _responsePlaneta.value = planetaResponse
    }

    suspend fun setResponseTextInMainThread(value : String) = withContext(Dispatchers.Main){
        _responseText.value = value
    }

    fun getAllPlanets() {
        viewModelScope.launch {

            withContext(Dispatchers.IO) {
                setIsVisibleInMainThread(true)

                val client = OkHttpClient()

                val request = Request.Builder()
                request.url("https://swapi.dev/api/planets/")


                val call = client.newCall(request.build())
                call.enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        println(e.toString())
                        CoroutineScope(Dispatchers.Main).launch {
                            delay(2000)
                            setResponseTextInMainThread("Algo ha ido mal")
                            setIsVisibleInMainThread(false)
                        }

                    }

                    override fun onResponse(call: Call, response: Response) {
                        println(response.toString())
                        response.body?.let { responseBody ->
                            val body = responseBody.string()
                            println(body)
                            val gson = Gson()

                            val planetaResponse = gson.fromJson(body, PlanetaResponse::class.java)

                            println(planetaResponse)


                            CoroutineScope(Dispatchers.Main).launch {
                                delay(2000)
                                setResponsePlanetaInMainThread(planetaResponse)
                                setIsVisibleInMainThread(false)
                            }
                        }
                    }
                })
            }
        }
    }

}