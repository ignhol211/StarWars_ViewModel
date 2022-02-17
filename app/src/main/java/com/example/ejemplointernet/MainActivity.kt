package com.example.ejemplointernet

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ejemplointernet.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TextoAdapter
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.getAllPlanets()

        initObserver()
    }

    private fun initObserver() {
        viewModel.responsePlaneta.observe(this) { planetaResponse ->
            adapter = TextoAdapter(planetaResponse.results)
            binding.recyclerView.adapter = adapter
        }

        viewModel.isVisible.observe(this) { isVisible ->
            if (isVisible) setVisible() else setGone()
        }

        viewModel.responseText.observe(this){responseText ->
            showSnackbar(responseText)
        }
    }

    private fun setVisible(){
        binding.pbDownloading.visibility = View.VISIBLE
    }

    private fun setGone(){
        binding.pbDownloading.visibility = View.INVISIBLE
    }

    private fun showSnackbar(text : String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
    }
}

