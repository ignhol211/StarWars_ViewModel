package com.example.ejemplointernet

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ejemplointernet.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    private val viewModel : SecondActivityViewModel by viewModels()

    companion object{
        const val URL = "url"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val initialData = intent.getStringExtra(URL)

        initObserver()

        binding.buttonDescargar.setOnClickListener(){
            initialData?.let{
                viewModel.getPlanet(initialData)
            }
        }

    }

    private fun initObserver() {
        viewModel.isVisible.observe(this){isVisible ->
            if(isVisible) setVisible() else setGone()

        }
        viewModel.responsePlaneta.observe(this){ planeta ->
            binding.tvNombre.text = planeta.name
            binding.tvPoblacion.text = planeta.population
            binding.tvClima.text = planeta.climate
        }

        viewModel.responseText.observe(this) { responseText ->
            showSnackbar(responseText)
        }

    }

    private fun showSnackbar(text : String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }

    private fun setVisible() {
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone() {
        binding.pbDownloading.visibility = View.GONE
    }

}
