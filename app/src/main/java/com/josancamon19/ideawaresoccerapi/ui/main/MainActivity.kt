package com.josancamon19.ideawaresoccerapi.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.View.VISIBLE
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.LENGTH_SHORT
import com.josancamon19.ideawaresoccerapi.R
import com.josancamon19.ideawaresoccerapi.databinding.ActivityMainBinding
import com.josancamon19.ideawaresoccerapi.ui.viewmodels.MainActivityViewModel
import dagger.android.support.DaggerAppCompatActivity


class MainActivity : DaggerAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    private val competitions = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.fab.setOnClickListener {
            showDialog()
        }
        setupViewModel()
    }

    private fun showDialog() {
        val competitionsString = mutableListOf("Display all")
        this.competitions.forEach {
            competitionsString.add(it)
        }
        val dialogBuilder = this.let { AlertDialog.Builder(it) }
        dialogBuilder.setIcon(R.drawable.ic_filter_list_black)
        dialogBuilder.setTitle("Filter by competition")
        dialogBuilder.setItems(competitionsString.toTypedArray()) { dialog, i ->
            val snackBarText = if(competitionsString[i] == "Display all"){
                "Removing Competition Filters"
            }else{
                "Filtering by ${competitionsString[i]} Competition"
            }
            val textColored = Html.fromHtml("<font color=\"#EAE7E0\">$snackBarText</font>")
            val snack = Snackbar.make(binding.coordinator,textColored ,LENGTH_SHORT)
            snack.show()
            viewModel.setCompetition(competitionsString[i])
            dialog.dismiss()
        }
        val alert = dialogBuilder.create()
        alert?.show()
    }

    @SuppressLint("RestrictedApi")
    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewModel.filterOptions.observe(this, Observer { competitionsList ->
            viewModel.filterOptions.removeObservers(this)
            competitionsList.forEach {
                competitions.add(it)
            }
        })

        viewModel.fabVisibility.observe(this,  Observer {
            if(it){
                binding.fab.visibility = VISIBLE
            }
        })
    }
}
