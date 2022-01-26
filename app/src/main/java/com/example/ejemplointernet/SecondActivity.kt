package com.example.ejemplointernet

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplointernet.databinding.ActivitySecondBinding
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    companion object{
        const val URL = "url"

        fun launch(context: Context,url:String){
            val intent = Intent (context,SecondActivity::class.java)
            intent.putExtra(URL,url)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialData = intent.getStringExtra(URL)

        //binding.tvTexto.text = initialData.

        binding.buttonDescargar.setOnClickListener(){

            val client = OkHttpClient()

            val request = Request.Builder()
            request.url(initialData.toString())


            val call = client.newCall(request.build())
            call.enqueue( object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println(e.toString())
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(this@SecondActivity, "Algo ha ido mal", Toast.LENGTH_SHORT).show()
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
                            binding.tvTexto.text = planeta.name
                            Toast.makeText(this@SecondActivity, planeta.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

        }

    }

}